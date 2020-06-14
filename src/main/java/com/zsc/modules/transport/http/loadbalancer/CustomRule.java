package com.zsc.modules.transport.http.loadbalancer;

import java.util.List;

public class CustomRule extends AbstractLoadBalancerRule {

    //private String businessId;

    public String choose(ILoadBalancer lb, Object key) {
        if (lb == null) {
            return null;
        }
        String server = null;

        while (server == null) {
            if (Thread.interrupted()) {
                return null;
            }
            List<String> upList = lb.getReachableServers();
            List<String> allList = lb.getAllServers();

            int serverCount = allList.size();
            if (serverCount == 0) {
                return null;
            }

            //int index =
        }
        return null;
    }

    @Override
    public String choose(Object key) {
        return null;
    }

}
