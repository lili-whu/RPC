package org.rpc.comsumer;

import org.rpc.model.User;
import org.rpc.server.RPCApplication;
import org.rpc.server.proxy.ServiceProxyFactory;
import org.rpc.service.UserService;

/**
 *
 */
public class UserConsumerExample{
    public static void main(String[] args) throws InterruptedException{

        // 通过工厂类, 获取远程服务提供者的代理对象
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
//        Thread.sleep(5000);
        User user = new User();
        user.setName("lili");
        for(int i = 0; i < 3; i ++){
            User newUser = userService.getUser(user);
            if(newUser != null){
                System.out.println(newUser.getName());
            }else{
                System.out.println("user == null");
            }
        }
    }
}
