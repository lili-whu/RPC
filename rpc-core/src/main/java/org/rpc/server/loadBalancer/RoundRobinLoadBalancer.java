package org.rpc.server.loadBalancer;

import org.rpc.server.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


// 轮询负载器
public class RoundRobinLoadBalancer implements LoadBalancer{

    private final AtomicInteger currentIndex = new AtomicInteger(0);
    @Override
    public ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList){
        if(serviceMetaInfoList.isEmpty()){
            // 服务不存在
            return null;
        }
        // 只有一个服务
        if(serviceMetaInfoList.size() == 1) return serviceMetaInfoList.get(0);

        // 通过计数模size得到结果
        int index = currentIndex.getAndIncrement() % serviceMetaInfoList.size();
        return serviceMetaInfoList.get(index);
    }

}
