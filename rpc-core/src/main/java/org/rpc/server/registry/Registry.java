package org.rpc.server.registry;

import org.rpc.server.config.RegistryConfig;
import org.rpc.server.model.ServiceMetaInfo;

import java.security.Provider;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface Registry{
    void init(RegistryConfig registryConfig);



    // 注册服务
    void register(ServiceMetaInfo serviceMetaInfo) throws ExecutionException, InterruptedException;
    // 取消服务
    void unRegister(ServiceMetaInfo serviceMetaInfo);

    // 服务发现, 返回服务列表
    List<ServiceMetaInfo> serviceDiscovery(String serviceKey);

    void destroy();

    // 心跳监测
    void heartBeat();

    // 对本地缓存key进行监听
    void watch(String serviceNodeKey);
}
