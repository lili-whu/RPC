package org.rpc.server.serializer;

import java.io.*;

/**
 * JDK序列化器
 */
public class JDKSerializer implements Serializer{

    @Override
    public <T> byte[] serialize(T object) throws IOException{
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ObjectOutputStream stream = new ObjectOutputStream(output);
        stream.writeObject(object);
        stream.close();
        return output.toByteArray();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T deserialize(byte[] bytes, Class<T> type) throws IOException{
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        try (ObjectInputStream stream = new ObjectInputStream(input)) {
            Object object = null;
            if((object = stream.readObject()) != null){
                // 存在泛型擦除问题, 不能使用instanceof去判断类型
                return (T) object;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return null;
    }
}
