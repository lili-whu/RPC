package org.rpc.server.fault.tolerant;

import org.rpc.server.model.RPCResponse;

import java.util.Map;


// 容错: 快速失败(通知外层调用方)
public class FailFastTolerantStrategy implements TolerantStrategy{
    @Override
    public RPCResponse doTolerant(Map<String, Object> context, Exception e){
        throw new RuntimeException("RPC服务错误", e);
    }
}
