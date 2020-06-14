package com.zsc.modules.transport.http;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.Map;

@Data
@ToString
@ConfigurationProperties(prefix = "httpclient.ribbon")
public class RibbonProperties {

    /**
     * 字符集类型
     */
    private String charset;

    /**
     * 最大连接数量
     */
    private Integer connMaxTotal;

    /**
     * 并发数量（针对一个访问地址的并发数量）
     */
    private Integer maxPerRoute;

    /**
     * 重试次数
     */
    private Integer retryNum;

    /**
     * 连接超时
     */
    private Integer connectTimeout;

    /**
     * 读取超时
     */
    private Integer readTimeout;

    /**
     * 连接不够用的的等待时间，不宜过长，必须设置，比如连接不够用时，时间过长将是灾难性的
     */
    private Integer requestTimeout;

    /**
     * 默认长连接保活时间
     */
    private Integer keepAliveTime;

    /**
     * 如果请求目标地址，单独配置长连接保持时间，使用该配置
     */
    private Map<String, Integer> keepAliveTargetHost;

    /**
     * http请求头
     */
    private Map<String, String> headers;
}
