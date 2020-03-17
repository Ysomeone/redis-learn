package com.yuan.redis.service;

import com.yuan.redis.controller.api.dto.LikedCountDTO;
import com.yuan.redis.entity.UserLike;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author yuan
 * @Date 2020/3/17 22:14
 * @Version 1.0
 */
public interface LikedService {

    void transLikedFromRedis2DB();


    void transLikedCountFromRedis2DB();
}
