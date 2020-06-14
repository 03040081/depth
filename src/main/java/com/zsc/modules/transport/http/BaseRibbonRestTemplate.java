package com.zsc.modules.transport.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class BaseRibbonRestTemplate {

    private RestTemplate restTemplate;

    private RibbonServerFactory ribbonServerFactory;

    protected void initRestTemplate(RestTemplate restTemplate, String ips) {
        this.restTemplate = restTemplate;
        this.ribbonServerFactory = new RibbonServerFactory();
        this.ribbonServerFactory.createServer(ips);
    }

    public JSONObject post(String url, Map<String, String> header, Map<String, Object> body) {
        String reqUrl = ribbonServerFactory.getServer() + url;
        log.info("BaseRibbonRestTemplate post reqUrl:{}, header:{}, body:{}", reqUrl, JSON.toJSONString(header), JSON.toJSONString(body));
        try {
            Set<Map.Entry<String, String>> entries = header.entrySet();
            MultiValueMap<String, String> headers = new HttpHeaders();
            for (Map.Entry<String, String> entry : entries) {
                List<String> objectList = new ArrayList<>();
                objectList.add(entry.getValue());
                headers.put(entry.getKey(), objectList);
            }
            HttpEntity<Map<String, Object>> formEntity = new HttpEntity<>(body, headers);
            ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(reqUrl, formEntity, JSONObject.class);
            if (responseEntity == null) {
                log.info("BaseRibbonRestTemplate post is null");
                return null;
            }
            JSONObject ret = responseEntity.getBody();
            log.info("BaseRibbonRestTemplate response entity:{}", ret);
            return ret;
        } catch (Exception e) {
            log.error("BaseRibbonRestTemplate :" + e);
            return null;
        }
    }
}
