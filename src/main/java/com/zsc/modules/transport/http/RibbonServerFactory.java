package com.zsc.modules.transport.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
public class RibbonServerFactory {

    private List<String> serverList;

    private Random iRule;

    private Integer bound;

    public void createServer(String ips) {
        serverList = new ArrayList<>();

        try {
            String[] hostAndPort = ips.split(",");
            for (String s : hostAndPort) {
                String host = StringUtils.substringBeforeLast(s, ":");
                String port = StringUtils.substringAfterLast(s, ":");
                if ("http".equals(host) || "https".equals(host) || StringUtils.isEmpty(host)) {
                    port = "https".equals(host) ? "443" : "80";
                    host = s;
                }
                if (StringUtils.isNotEmpty(host) && StringUtils.isNotEmpty(port)) {
                    serverList.add(host + ":" + port);
                } else {
                    throw new Exception("init servers ip is error " +  s);
                }
            }
         } catch (Exception e) {
            log.error(e.getMessage());
        }

        iRule = new Random();
        bound = serverList.size();
        log.info("create Servers ips:{}", ips);
    }

    public String getServer() {
        return serverList.get(iRule.nextInt(bound));
    }
}
