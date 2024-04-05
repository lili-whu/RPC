package org.rpc.server.registry;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rpc.server.config.RegistryConfig;
import org.rpc.server.model.ServiceMetaInfo;

import java.util.List;

public class RegistryTest extends TestCase{

        final Registry registry = new EtcdRegistry();


        public void init() {
            RegistryConfig registryConfig = new RegistryConfig();
            registryConfig.setAddress("http://localhost:2379");
            registry.init(registryConfig);
        }

        public void testRegister() throws Exception {
            init();
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName("myService");
            serviceMetaInfo.setServiceVersion("1.0");
            serviceMetaInfo.setServiceHost("localhost");
            serviceMetaInfo.setServicePort(1234);
            registry.register(serviceMetaInfo);
            serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName("myService");
            serviceMetaInfo.setServiceVersion("1.0");
            serviceMetaInfo.setServiceHost("localhost");
            serviceMetaInfo.setServicePort(1235);
            registry.register(serviceMetaInfo);
            serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName("myService");
            serviceMetaInfo.setServiceVersion("2.0");
            serviceMetaInfo.setServiceHost("localhost");
            serviceMetaInfo.setServicePort(1234);
            registry.register(serviceMetaInfo);
            Assert.assertNotNull(registry);
        }

        public void unRegister() {
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName("myService");
            serviceMetaInfo.setServiceVersion("1.0");
            serviceMetaInfo.setServiceHost("localhost");
            serviceMetaInfo.setServicePort(1234);
            registry.unRegister(serviceMetaInfo);
        }

        public void serviceDiscovery() {
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName("myService");
            serviceMetaInfo.setServiceVersion("1.0");
            String serviceKey = serviceMetaInfo.getServiceKey();
            List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceKey);
            Assert.assertNotNull(serviceMetaInfoList);
        }

        public void heartBeat() throws Exception {
            // init 方法中已经执行心跳检测了
            testRegister();
            // 阻塞 1 分钟
            Thread.sleep(60 * 1000L);
        }

}