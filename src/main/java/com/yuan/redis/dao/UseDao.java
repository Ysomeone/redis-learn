package com.yuan.redis.dao;

import com.yuan.redis.entity.User;

import java.util.List;

/**
 * @Author yuan
 * @Date 2020/3/14 15:14
 * @Version 1.0
 */
public interface UseDao {

    List<User> getAll();

    User getOne(Long id);

    void insert(User user);

    void update(User user);

    void delete(Long id);

}