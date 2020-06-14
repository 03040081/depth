package com.zsc.modules.transport.http.loadbalancer;

import java.util.List;

public interface ILoadBalancer {

    void addServers(List<String> newServers);

    String chooseServer(Object key);

    void markServerDown(String server);

    List<String> getReachableServers();

    List<String> getAllServers();
}
