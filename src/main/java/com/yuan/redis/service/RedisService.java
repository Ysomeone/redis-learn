package com.yuan.redis.service;

/**
 * @Author yuan
 * @Date 2020/3/14 18:12
 * @Version 1.0
 */
public interface RedisService {

    String getSet(String key, String value);

    void set(String key, String value, Long time);

    Boolean setnx(String key, String value);

     void test();

}