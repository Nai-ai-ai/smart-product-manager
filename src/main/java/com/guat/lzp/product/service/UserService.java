package com.guat.lzp.product.service;

import com.guat.lzp.product.entity.User;

/**
 * 用户服务接口
 */
public interface UserService {

    /** 根据用户名查询用户 */
    User findByUsername(String username);

    /** 新增用户 */
    boolean save(User user);
}
