package org.rpc.server.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.rpc.server.constant.RPCConstant;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
public class RPCRequest implements Serializable{
    // 服务名
    private String serviceName;
    //方法名
    private String methodName;
    // 服务版本
    private String serviceVersion = RPCConstant.DEFAULT_VERSION;
    // 方法参数类型
    private Class<?>[] parameterType;
    // 方法参数
    private Object[] args;
}
