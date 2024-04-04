package org.rpc.server.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 注册中心(本地实现)
 */
public class LocalRegistry{
    public static final Map<String, Class<?>> map = new ConcurrentHashMap<>();

    /**
     * 服务注册
     */
    public static void register(String serviceName, Class<?> classImpl){
        map.put(serviceName, classImpl);
    }

    /**
     * 服务获取
     *
     * @param serviceName 服务名
     * @return 服务class
     */
    public static Class<?> get(String serviceName){
        return map.get(serviceName);
    }
}
