package com.yuan.redis.service;

import com.yuan.redis.entity.User;

import java.util.List;

/**
 * @Author yuan
 * @Date 2020/3/14 15:46
 * @Version 1.0
 */
public interface UserService {
    List<User> getAll();

    User getOne(Long id);

    Long insert(User user);

    Long update(User user);

    Long delete(Long id);
}
