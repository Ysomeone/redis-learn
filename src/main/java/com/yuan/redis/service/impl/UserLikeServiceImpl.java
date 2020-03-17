package com.yuan.redis.service.impl;

import com.yuan.redis.dao.UserLikeDao;
import com.yuan.redis.entity.UserLike;
import com.yuan.redis.service.UserLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserLikeServiceImpl implements UserLikeService {

    @Autowired
    private UserLikeDao userLikeDao;


    @Override
    public List<UserLike> findListByPage(Object parameter) {
        return userLikeDao.findListByPage(parameter);
    }

    @Override
    public List<UserLike> findListNewByPage(Object parameter) {
        return userLikeDao.findListNewByPage(parameter);
    }


    @Override
    public Long deletes(Object parameter) {
        return userLikeDao.deletes(parameter);
    }

    @Override
    public Long insert(Object parameter) {
        return userLikeDao.insert(parameter);
    }

    @Override
    public Long update(Object parameter) {
        return userLikeDao.update(parameter);
    }


}
