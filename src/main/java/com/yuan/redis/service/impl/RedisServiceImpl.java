package com.yuan.redis.service.impl;

import com.yuan.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author yuan
 * @Date 2020/3/14 18:12
 * @Version 1.0
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String getSet(String key, String value) {
        return (String) redisTemplate.opsForValue().getAndSet(key, value);
    }

    @Override
    public void set(String key, String value, Long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    @Override
    public Boolean setnx(String key, String value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    @Override
    public void test() {
        redisTemplate.opsForHash().put("yuan", "a1", "1");
        redisTemplate.opsForHash().put("yuan", "a2", "1");
        redisTemplate.opsForHash().put("yuan", "a3", "1");
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("a4", "12");
        map.put("a5", "3");
        redisTemplate.opsForHash().putAll("yuan", map);
        String o = (String) redisTemplate.opsForHash().get("yuan", "a4");
        Boolean flag = redisTemplate.opsForHash().hasKey("yuan", "a4");
        redisTemplate.opsForHash().increment("yuan","a1",3L);
        Set yuan = redisTemplate.opsForHash().keys("yuan");
        System.out.println("00000000000000000000000" + o + "flag:" + flag);
        Map<String,Object> yuan1 = redisTemplate.opsForHash().entries("yuan");

        redisTemplate.opsForList().leftPush("yuan1","1");
        redisTemplate.opsForList().rightPush("yuan1","3");
        redisTemplate.opsForList().set("yuan1",1L,"2");
        redisTemplate.opsForList().range("yuan",1,3);




    }

    public static void main(String[] args) {
    }

}
