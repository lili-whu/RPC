package org.rpc.server.fault.tolerant;

import org.rpc.server.model.RPCResponse;

import java.util.Map;

//故障转移到其他服务节点(根据上下文节点选择其他节点进行调用)
public class FailOverTolerantStrategy implements TolerantStrategy{
    @Override
    public RPCResponse doTolerant(Map<String, Object> context, Exception e){
        return null;
    }
}