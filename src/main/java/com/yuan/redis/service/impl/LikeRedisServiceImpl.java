package com.yuan.redis.service.impl;

import com.yuan.redis.controller.api.dto.LikedCountDTO;
import com.yuan.redis.entity.UserLike;
import com.yuan.redis.enums.LinkedStatusEnum;
import com.yuan.redis.service.LikeRedisService;
import com.yuan.redis.toolkit.RedisKeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author yuan
 * @Date 2020/3/15 22:57
 * @Version 1.0
 */
@Service
public class LikeRedisServiceImpl implements  LikeRedisService {

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public void saveLike(String likedUserId, String likedPostId) {
        String key = RedisKeyUtils.getLikedKey(likedUserId, likedPostId);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER, key, LinkedStatusEnum.LIKE.getCode()+"");
    }
    @Override
    public void unlike(String likedUserId, String likedPostId) {
        String key = RedisKeyUtils.getLikedKey(likedUserId, likedPostId);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER, key, LinkedStatusEnum.UNLIKE.getCode());
    }
    @Override
    public void dellike(String likedUserId, String likedPostId) {
        String key = RedisKeyUtils.getLikedKey(likedUserId, likedPostId);
        redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER, key);
    }

    @Override
    public void incrLikedCount(String likedUserId, String likedPostId) {
        String key = RedisKeyUtils.getLikedKey(likedUserId, likedPostId);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT, likedUserId, 1);
    }

    @Override
    public void decrLikedCount(String likedUserId, String likedPostId) {
        String key = RedisKeyUtils.getLikedKey(likedUserId, likedPostId);
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT, likedUserId, -1);
    }
    @Override
    public List<UserLike> getLikedDataFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_USER, ScanOptions.NONE);
        ArrayList<UserLike> list = new ArrayList<>();
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> entry = cursor.next();
            String key = (String) entry.getKey();
            String[] split = key.split("::");
            String likedUserId = split[0];
            String likePostId = split[1];
            String value = (String) entry.getValue();
            Integer num = Integer.valueOf(value);
            UserLike userLike = new UserLike(likedUserId, likePostId, num);
            list.add(userLike);
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER, key);
        }
        return list;
    }


    @Override
    public List<LikedCountDTO> getLikedCountFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT, ScanOptions.NONE);
        ArrayList<LikedCountDTO> list = new ArrayList<>();
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> map = cursor.next();
            String key = (String) map.getKey();
            LikedCountDTO likedCountDTO = new LikedCountDTO(key, (Integer) map.getValue());
            list.add(likedCountDTO);
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT, key);
        }
        return list;
    }


}
