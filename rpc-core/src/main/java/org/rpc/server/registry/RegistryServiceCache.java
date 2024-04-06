package org.rpc.server.registry;

import org.rpc.server.model.ServiceMetaInfo;

import java.util.List;

public class RegistryServiceCache{
    // 服务信息的缓存
    List<ServiceMetaInfo> serviceCache;

    void writeCache(List<ServiceMetaInfo> newCache){
        this.serviceCache = newCache;
    }
    List<ServiceMetaInfo> readCache(){
        return this.serviceCache;
    }

    void clearCache(){
        this.serviceCache = null;
    }
}
