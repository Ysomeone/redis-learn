package com.yuan.redis.service.impl;

import com.yuan.redis.dao.UserDao;
import com.yuan.redis.entity.User;
import com.yuan.redis.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * SERVICE - User(用户)
 * @Transactional ***对表进行修改，添加时，记得加这个事务处理，尤其是下订单的业务，多表的业务处理***例子看GenericServiceImpl
 * @author
 * @version 2.0
 */
@Service
public class AppUserServiceImpl extends GenericServiceImpl<User, Long> implements AppUserService {
	
	@Autowired
	private UserDao userDao;
	
	
	@Autowired
	public void setGenericDao() {
		super.setGenericDao(userDao);
	}

	public List<User> findListByPage(Object parameter){
		return userDao.findListByPage(parameter);
	}
	
	public List<User> findListNewByPage(Object parameter) {
		return userDao.findListNewByPage(parameter);
	}
	
	public Long deletes(Object parameter){
		return userDao.deletes( parameter);
	}
	
	
}
