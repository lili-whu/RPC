package org.rpc.server.serializer;

import java.util.HashMap;
import java.util.Map;

/**
 * 序列化器工厂
 */
public class SerializerFactory{
    private static final Map<String, Serializer> SERIALIZER_MAP = new HashMap<String, Serializer>();
    // 静态代码段初始化
    static{
        SERIALIZER_MAP.put(SerializerKeys.JDK, new JDKSerializer());
        SERIALIZER_MAP.put(SerializerKeys.JSON, new JsonSerializer());
        SERIALIZER_MAP.put(SerializerKeys.HESSIAN, new HessianSerializer());
    }

    private static final Serializer DEFAULT_SERIALIZER = SERIALIZER_MAP.get(SerializerKeys.JDK);

    public static Serializer getInstance(String key){
        return SERIALIZER_MAP.getOrDefault(key, DEFAULT_SERIALIZER);
    }
}
