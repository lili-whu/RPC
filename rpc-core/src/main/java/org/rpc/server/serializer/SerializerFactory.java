package org.rpc.server.serializer;

import org.rpc.server.spi.SpiLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * 序列化器工厂
 */
public class SerializerFactory{

    // 静态代码初始化通过SPI读取可加载的类信息
    static{
        SpiLoader.load(Serializer.class);
    }

    /**
     * 通过key得到对应的序列化器
     */
    public static Serializer getInstance(String key){
        return SpiLoader.getInstance(Serializer.class, key);
    }
}



//public class SerializerFactory{
//    private static final Map<String, Serializer> SERIALIZER_MAP = new HashMap<String, Serializer>();
//    // 静态代码段初始化
//    static{
//        SERIALIZER_MAP.put(SerializerKeys.JDK, new JDKSerializer());
//        SERIALIZER_MAP.put(SerializerKeys.JSON, new JsonSerializer());
//        SERIALIZER_MAP.put(SerializerKeys.HESSIAN, new HessianSerializer());
//    }
//
//    private static final Serializer DEFAULT_SERIALIZER = SERIALIZER_MAP.get(SerializerKeys.JDK);
//
//    public static Serializer getInstance(String key){
//        return SERIALIZER_MAP.getOrDefault(key, DEFAULT_SERIALIZER);
//    }
//}
