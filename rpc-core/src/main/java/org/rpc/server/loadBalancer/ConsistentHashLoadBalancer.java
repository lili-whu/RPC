package org.rpc.server.loadBalancer;

import org.rpc.server.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

// 一致性hash负载均衡
public class ConsistentHashLoadBalancer implements LoadBalancer{

    private final TreeMap<Integer, ServiceMetaInfo> virtualNodes = new TreeMap<>();

    private static final int VIRTUAL_NODE_COUNT = 100;
    @Override
    public ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList){
        if(serviceMetaInfoList.isEmpty()) return null;

        for(ServiceMetaInfo serviceMetaInfo: serviceMetaInfoList){
            // 构造虚拟节点
            for(int i = 0; i < VIRTUAL_NODE_COUNT; i++){
                int hash = getHash(serviceMetaInfo.getServiceAddress() + "#" + i);
                virtualNodes.put(hash, serviceMetaInfo);
            }
        }
        int hash = getHash(requestParams);

        // 选择最接近且大于hash值的虚拟节点
        Map.Entry<Integer, ServiceMetaInfo> entry = virtualNodes.ceilingEntry(hash);
        if(entry == null){
            // 返回头节点
            entry = virtualNodes.firstEntry();
        }
        return entry.getValue();
    }

    // 计算hash值
    public int getHash(Object key){
        return key.hashCode();
    }
}
