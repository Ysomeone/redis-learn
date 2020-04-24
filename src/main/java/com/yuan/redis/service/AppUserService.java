package com.yuan.redis.service;

import com.yuan.redis.entity.User;
import com.yuan.redis.service.GenericService;

import java.util.List;

/**
 * SERVICE - User(用户)
 *
 * @author
 * @version 2.0
 */
public interface AppUserService extends GenericService<User, Long> {

    List<User> findListByPage(Object parameter);

    List<User> findListNewByPage(Object parameter);

    Long deletes(Object parameter);
}
