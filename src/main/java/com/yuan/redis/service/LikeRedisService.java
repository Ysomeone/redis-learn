package com.yuan.redis.service;

import com.yuan.redis.controller.api.dto.LikedCountDTO;
import com.yuan.redis.entity.UserLike;
import com.yuan.redis.enums.LinkedStatusEnum;
import com.yuan.redis.toolkit.RedisKeyUtils;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 点赞的redis操作
 *
 * @Author yuan
 * @Date 2020/3/15 22:53
 * @Version 1.0
 */
public interface LikeRedisService {
    public void saveLike(String likedUserId, String likedPostId);

    public void unlike(String likedUserId, String likedPostId);

    public void dellike(String likedUserId, String likedPostId);

    public void incrLikedCount(String likedUserId, String likedPostId);

    public void decrLikedCount(String likedUserId, String likedPostId);

    public List<UserLike> getLikedDataFromRedis();

    public List<LikedCountDTO> getLikedCountFromRedis();


}
