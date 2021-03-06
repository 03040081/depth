package com.zsc.modules.transport.http.loadbalancer;

public abstract class AbstractLoadBalancerRule implements IRule {

    private ILoadBalancer lb;

    @Override
    public void setLoadBalancer(ILoadBalancer lb) {
        this.lb = lb;
    }

    @Override
    public ILoadBalancer getLoadBalancer() {
        return lb;
    }
}
