package com.yuan.redis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author yuan
 * @Date 2020/3/19 23:35
 * @Version 1.0
 */
@Service
public class TokenServiceImpl {

    private static final String TOKEN_NAME = "token";

    @Resource
    private RedisTemplate redisTemplate;










}
