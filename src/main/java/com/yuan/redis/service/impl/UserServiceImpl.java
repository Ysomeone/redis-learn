package com.yuan.redis.service.impl;

import com.yuan.redis.dao.UseDao;
import com.yuan.redis.entity.User;
import com.yuan.redis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author yuan
 * @Date 2020/3/14 15:46
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UseDao userDao;

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User getOne(Long id) {
        return userDao.getOne(id);
    }

    @Override
    public Long insert(User user) {
        userDao.insert(user);
        return 1L;
    }

    @Override
    public Long update(User user) {
        userDao.update(user);
        return 1L;
    }

    @Override
    public Long delete(Long id) {
        userDao.delete(id);
        return 1L;
    }
}
