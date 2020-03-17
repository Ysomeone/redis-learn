package com.yuan.redis.service.impl;

import com.yuan.redis.controller.api.dto.LikedCountDTO;
import com.yuan.redis.entity.UserLike;
import com.yuan.redis.service.LikeRedisService;
import com.yuan.redis.service.LikedService;
import com.yuan.redis.service.UserLikeService;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author yuan
 * @Date 2020/3/17 22:14
 * @Version 1.0
 */
public class LikedServiceImpl implements LikedService {


    @Autowired
    private LikeRedisService redisService;

    @Autowired
    private UserLikeService userLikeService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void transLikedFromRedis2DB() {
        List<UserLike> list = redisService.getLikedDataFromRedis();
        for (UserLike like : list) {

        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void transLikedCountFromRedis2DB() {
        List<LikedCountDTO> list = redisService.getLikedCountFromRedis();
        for (LikedCountDTO dto : list) {

        }
    }
}
