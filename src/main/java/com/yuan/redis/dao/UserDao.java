package com.yuan.redis.dao;


import com.yuan.redis.entity.User;

import java.util.List;

/**
 * DAO - User(用户)
 * 
 * @author wmj
 * @version 2.0
 */
public interface UserDao  extends GenericDao<User, Long> {
	List<User> findListByPage(Object parameter);
	List<User> findListNewByPage(Object parameter);
    Long deletes(Object parameter);
}
