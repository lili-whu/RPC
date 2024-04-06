package org.rpc.server.fault.tolerant;

import org.rpc.server.model.RPCResponse;

import java.util.Map;

public interface TolerantStrategy{

    RPCResponse doTolerant(Map<String, Object> context, Exception e);
}
