package org.rpc.server.proxy;

import java.lang.reflect.Proxy;

/**
 * 代理工厂, 创建动态代理对象
 */
public class ServiceProxyFactory{
    /**
     * 根据接口动态获取代理对象
     * @param serviceClass
     * @return
     * @param <T>
     */
    @SuppressWarnings("unchecked")
    public static <T> T getProxy(Class<T> serviceClass){
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new ServiceDynamicProxy()
        );
    }
}
