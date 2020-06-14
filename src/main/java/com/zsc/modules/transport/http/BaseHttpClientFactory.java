package com.zsc.modules.transport.http;

import com.alibaba.fastjson.JSON;
import jdk.nashorn.internal.runtime.options.Option;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.Assert;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
@ConditionalOnClass(value = {RestTemplate.class, CloseableHttpClient.class})
@Import(RibbonProperties.class)
public class BaseHttpClientFactory {

    private RibbonProperties ribbonProperties;

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        Assert.isTrue(ribbonProperties.getConnMaxTotal() > 0,
                "httpclient invalid connMaxTotal:" + ribbonProperties.getConnMaxTotal());
        Assert.isTrue(ribbonProperties.getMaxPerRoute() > 0,
                "httpclient invalid maxPerRoute:" + ribbonProperties.getMaxPerRoute());

        HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = httpComponentsClientHttpRequestFactory();

        return httpComponentsClientHttpRequestFactory;
    }

    protected HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory() {
        //创建request工程
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        //连接超时
        clientHttpRequestFactory.setConnectTimeout(ribbonProperties.getConnectTimeout());
        //数据读取超时
        clientHttpRequestFactory.setReadTimeout(ribbonProperties.getReadTimeout());
        //连接不够用时的等待时间
        clientHttpRequestFactory.setConnectionRequestTimeout(ribbonProperties.getRequestTimeout());
        clientHttpRequestFactory.setHttpClient(httpClient());

        return clientHttpRequestFactory;
    }

    @Bean
    protected HttpClient httpClient() {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

        try {
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .register("https", SSLConnectionSocketFactory.getSocketFactory())
                    .build();
            PoolingHttpClientConnectionManager httpClientConnectionManager = new PoolingHttpClientConnectionManager(registry);

            httpClientConnectionManager.setMaxTotal(ribbonProperties.getConnMaxTotal());

            httpClientConnectionManager.setDefaultMaxPerRoute(ribbonProperties.getMaxPerRoute());

            httpClientBuilder.setConnectionManager(httpClientConnectionManager);

            httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(ribbonProperties.getRetryNum(),
                    ribbonProperties.getRetryNum() != 0));
            List<Header> headers = getHeaders();
            if (headers != null && headers.size() > 0) {
                httpClientBuilder.setDefaultHeaders(headers);
            }

            httpClientBuilder.setKeepAliveStrategy(connectionKeepAliveStrategy());

            httpClientBuilder.evictIdleConnections(30L, TimeUnit.SECONDS);

            return httpClientBuilder.build();
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return null;
    }

    protected ConnectionKeepAliveStrategy connectionKeepAliveStrategy() {
        return (response, context) -> {
            HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
            while (it.hasNext()) {
                HeaderElement he = it.nextElement();
                log.info("HeaderElement:{}", JSON.toJSONString(he));
                String param = he.getName();
                String value = he.getValue();
                if (value != null && "timeout".equalsIgnoreCase(param)) {
                    try {
                        return Long.parseLong(value) * 1000L;
                    } catch (NumberFormatException e) {
                        log.error("resolve Keep-Alive timeout", e);
                    }
                }
            }
            HttpHost target = (HttpHost) context.getAttribute(HttpClientContext.HTTP_TARGET_HOST);
            Optional<Map.Entry<String, Integer>> any = Optional.ofNullable(ribbonProperties.getKeepAliveTargetHost())
                    .orElseGet(HashMap::new)
                    .entrySet()
                    .stream()
                    .filter(e -> e.getKey().equalsIgnoreCase(target.getHostName()))
                    .findAny();
            int keepAliveTime = ribbonProperties.getKeepAliveTime() == null ? 60 : ribbonProperties.getKeepAliveTime();
            return any.map(en -> en.getValue() * 1000L).orElse(keepAliveTime * 1000L);
        };
    }

    protected List<Header> getHeaders() {
        List<Header> headers = new ArrayList<>();
        if (ribbonProperties.getHeaders() == null) {
            log.warn("init headers is null");
            return headers;
        }
        for (Map.Entry<String, String> entry : ribbonProperties.getHeaders().entrySet()) {
            headers.add(new BasicHeader(entry.getKey(), entry.getValue()));
        }
        return headers;
    }

    protected void modifyDefaultCharset(RestTemplate restTemplate) {
        List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
        HttpMessageConverter<?> converter = null;
        for (HttpMessageConverter<?> item : converterList) {
            if (StringHttpMessageConverter.class == item.getClass()) {
                log.info("HttpMessageConverter exist null");
                converter = item;
                break;
            }
        }
        if (null != converter) {
            converterList.remove(converter);
        }
        Charset defaultCharset = Charset.forName(ribbonProperties.getCharset());
        converterList.add(1, new StringHttpMessageConverter(defaultCharset));
    }

    public RestTemplate createRestTemplate(ClientHttpRequestFactory factory) {
        RestTemplate restTemplate = new RestTemplate(factory);

        modifyDefaultCharset(restTemplate);

        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());

        return restTemplate;
    }

    @Bean(name = "httpClientRestTemplate")
    public RestTemplate restTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {
        return createRestTemplate(clientHttpRequestFactory);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
