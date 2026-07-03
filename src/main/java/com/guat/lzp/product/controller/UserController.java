package com.guat.lzp.product.controller;

import com.guat.lzp.product.entity.ApiResult;
import com.guat.lzp.product.entity.User;
import com.guat.lzp.product.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户管理控制器
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /** 用户注册 */
    @PostMapping("/register")
    public ApiResult<String> register(@RequestBody User user) {
        // 检查用户名是否已存在
        if (userService.findByUsername(user.getUsername()) != null) {
            return ApiResult.error("用户名已存在");
        }
        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(1);
        boolean success = userService.save(user);
        return success ? ApiResult.success("注册成功", null) : ApiResult.error("注册失败");
    }

    /** 查询所有用户 */
    @GetMapping("/list")
    public ApiResult<List<User>> list() {
        List<User> users = userService.findAll();
        // 隐藏密码
        users.forEach(u -> u.setPassword(null));
        return ApiResult.success(users);
    }

    /** 根据ID查询用户 */
    @GetMapping("/{id}")
    public ApiResult<User> getById(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return ApiResult.error("用户不存在");
        }
        user.setPassword(null);
        return ApiResult.success(user);
    }

    /** 新增用户 */
    @PostMapping("/save")
    public ApiResult<String> add(@RequestBody User user) {
        // 检查用户名是否已存在
        if (userService.findByUsername(user.getUsername()) != null) {
            return ApiResult.error("用户名已存在");
        }
        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(1);
        boolean success = userService.save(user);
        return success ? ApiResult.success("新增成功", null) : ApiResult.error("新增失败");
    }

    /** 更新用户信息 */
    @PostMapping("/update")
    public ApiResult<String> update(@RequestBody User user) {
        // 如果密码为空，不更新密码
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            user.setPassword(null);
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        boolean success = userService.update(user);
        return success ? ApiResult.success("更新成功", null) : ApiResult.error("更新失败");
    }

    /** 删除用户 */
    @PostMapping("/delete/{id}")
    public ApiResult<String> delete(@PathVariable Long id) {
        boolean success = userService.deleteById(id);
        return success ? ApiResult.success("删除成功", null) : ApiResult.error("删除失败");
    }
}
