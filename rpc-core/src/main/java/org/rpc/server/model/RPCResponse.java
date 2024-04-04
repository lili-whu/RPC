package org.rpc.server.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RPCResponse implements Serializable{
    // 返回数据
    private Object data;
    private Class<?> dataType;

    // 消息
    private String message;
    // 异常
    private Exception exception;
}
