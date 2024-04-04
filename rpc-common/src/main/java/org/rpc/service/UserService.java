package org.rpc.service;

import org.rpc.model.User;

/**
 * 用户服务
 */
public interface UserService{
    /**
     * 获取用户
     * @param user
     * @return
     */
    User getUser(User user);
}
