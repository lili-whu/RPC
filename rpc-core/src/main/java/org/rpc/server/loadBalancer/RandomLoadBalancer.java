package org.rpc.server.loadBalancer;

import org.rpc.server.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomLoadBalancer implements LoadBalancer{

    private final Random rand = new Random();
    @Override
    public ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList){
        int size = serviceMetaInfoList.size();
        if(size == 0) return null;
        if(size == 1) return serviceMetaInfoList.get(0);
        return serviceMetaInfoList.get(rand.nextInt(size));
    }
}
