package org.rpc.server.loadBalancer;


import org.rpc.server.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;

// 负载均衡(消费端)
public interface LoadBalancer{
    // 选择合适的服务
    ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList);

}
