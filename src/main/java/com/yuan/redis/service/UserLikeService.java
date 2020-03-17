package com.yuan.redis.service;

import com.yuan.redis.entity.UserLike;

import java.util.List;
/**
 * SERVICE - UserLike(用户点赞表)
 * 
 * @author wmj
 * @version 2.0
 */
public interface UserLikeService   {
	public	List<UserLike> findListByPage(Object parameter);
	public	List<UserLike> findListNewByPage(Object parameter);
    public	 Long deletes(Object parameter);
}
