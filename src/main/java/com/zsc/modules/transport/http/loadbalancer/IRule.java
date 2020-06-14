package com.zsc.modules.transport.http.loadbalancer;

public interface IRule {

    String choose(Object key);

    void setLoadBalancer(ILoadBalancer lb);

    ILoadBalancer getLoadBalancer();
}
