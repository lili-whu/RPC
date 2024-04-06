package org.rpc.server.fault.tolerant;

import org.rpc.server.model.RPCResponse;

import java.util.Map;


// 静默处理
public class FailSafeTolerantStrategy implements TolerantStrategy{

    @Override
    public RPCResponse doTolerant(Map<String, Object> context, Exception e){
        System.err.println("静默处理异常: " + e);
        return new RPCResponse();
    }
}
