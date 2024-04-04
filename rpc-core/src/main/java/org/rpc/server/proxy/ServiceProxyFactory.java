package org.rpc.server.proxy;

import org.rpc.server.RPCApplication;
import org.rpc.server.config.RPCConfig;

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
        if(RPCApplication.getConfig().getMock()){
            return getMockProxy(serviceClass);
        }
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new ServiceDynamicProxy()
        );
    }
    @SuppressWarnings("unchecked")
    public static <T> T getMockProxy(Class<T> serviceClass){
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new MockServiceProxy()
        );
    }
}
