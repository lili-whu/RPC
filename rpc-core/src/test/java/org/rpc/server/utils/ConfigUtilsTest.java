package org.rpc.server.utils;

import junit.framework.TestCase;
import org.rpc.server.RPCApplication;
import org.rpc.server.config.RPCConfig;
import org.rpc.server.constant.RPCConstant;

public class ConfigUtilsTest extends TestCase{
    public void testUtils(){
        RPCConfig config = ConfigUtils.loadConfig(RPCConfig.class, RPCConstant.DEFAULT_PREFIX);
        System.out.println(config.toString());
    }
    public void testRPCConfigApp(){
        RPCConfig config = RPCApplication.getConfig();
        System.out.println(config.toString());
    }
}