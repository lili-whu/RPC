package org.rpc.server.proxy;

import org.rpc.model.User;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * mock服务
 */
public class MockServiceProxy implements InvocationHandler{

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{
        Class<?> methodReturnType = method.getReturnType();
        return getObject(methodReturnType);
    }

    private Object getObject(Class<?> type){
        if(type.isPrimitive()){
            if(type == boolean.class){
                return false;
            }else if(type == short.class){
                return (short) 0;
            }else if(type == int.class) {
                return 0;
            }else if(type == long.class){
                return 0L;
            }
        }
        if(type == String.class){
            return "this is mock";
        }
        if(type == User.class){
            User u = new User();
            u.setName("this is mock user");
            return u;
        }
        return null;
    }
}

