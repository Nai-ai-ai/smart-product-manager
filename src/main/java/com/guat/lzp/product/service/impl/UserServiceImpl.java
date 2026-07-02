package com.guat.lzp.product.service.impl;

import com.guat.lzp.product.entity.User;
import com.guat.lzp.product.mapper.UserMapper;
import com.guat.lzp.product.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public boolean save(User user) {
        return userMapper.insert(user) > 0;
    }
}
