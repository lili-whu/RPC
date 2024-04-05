package org.rpc.comsumer;

import org.rpc.model.User;
import org.rpc.server.RPCApplication;
import org.rpc.server.proxy.ServiceProxyFactory;
import org.rpc.service.UserService;

/**
 *
 */
public class UserConsumerExample{
    public static void main(String[] args){

        // 通过工厂类, 获取远程服务提供者的代理对象
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("lili");
        User newUser = userService.getUser(user);
        if(newUser != null){
            System.out.println(newUser.getName());
        }else{
            System.out.println("user == null");
        }
    }
}
