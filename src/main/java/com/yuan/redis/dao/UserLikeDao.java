package com.yuan.redis.dao;

import com.yuan.redis.entity.UserLike;

import java.util.List;

/**
 * DAO - UserLike(用户点赞表)
 *
 * @author wmj
 * @version 2.0
 */
public interface UserLikeDao {
    List<UserLike> findListByPage(Object parameter);

    List<UserLike> findListNewByPage(Object parameter);

    Long deletes(Object parameter);

    Long insert(Object parameter);

    Long update(Object parameter);
}
