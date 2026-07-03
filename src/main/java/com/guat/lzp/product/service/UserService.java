package com.guat.lzp.product.service;

import com.guat.lzp.product.entity.User;
import java.util.List;

/**
 * 用户服务接口
 */
public interface UserService {

    /** 根据用户名查询用户 */
    User findByUsername(String username);

    /** 查询所有用户 */
    List<User> findAll();

    /** 根据ID查询用户 */
    User findById(Long id);

    /** 新增用户 */
    boolean save(User user);

    /** 更新用户信息 */
    boolean update(User user);

    /** 根据ID删除用户 */
    boolean deleteById(Long id);

    /** 统计全部用户数量 */
    int countAll();
}
