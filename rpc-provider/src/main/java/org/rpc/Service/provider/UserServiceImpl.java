package org.rpc.Service.provider;


import org.rpc.model.User;
import org.rpc.service.UserService;

public class UserServiceImpl implements UserService{

    @Override
    public User getUser(User user){
        System.out.println("getUser: " + user.getName());
        return user;
    }
}
