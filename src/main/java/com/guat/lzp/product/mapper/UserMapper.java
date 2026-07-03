package com.guat.lzp.product.mapper;

import com.guat.lzp.product.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户数据访问接口
 */
@Mapper
public interface UserMapper {

    /** 根据用户名查询用户 */
    User findByUsername(@Param("username") String username);

    /** 根据用户ID查询角色列表 */
    List<String> findRolesByUserId(@Param("userId") Long userId);

    /** 查询所有用户 */
    List<User> findAll();

    /** 根据ID查询用户 */
    User findById(@Param("id") Long id);

    /** 新增用户 */
    int insert(User user);

    /** 更新用户信息 */
    int update(User user);

    /** 根据ID删除用户 */
    int deleteById(@Param("id") Long id);

    /** 统计全部用户数量 */
    int countAll();
}
