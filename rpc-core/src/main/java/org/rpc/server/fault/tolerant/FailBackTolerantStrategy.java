package org.rpc.server.fault.tolerant;

import org.rpc.server.model.RPCResponse;

import java.util.Map;


// 降级到其他服务(本地mock服务等)
public class FailBackTolerantStrategy implements TolerantStrategy{
    @Override
    public RPCResponse doTolerant(Map<String, Object> context, Exception e){
        return null;
    }
}
