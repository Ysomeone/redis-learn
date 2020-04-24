package com.yuan.redis.service.impl;

import com.yuan.redis.dao.SysLogDao;
import com.yuan.redis.entity.SysLog;
import com.yuan.redis.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * SERVICE - SysLog(日志记录表)
 * @Transactional ***对表进行修改，添加时，记得加这个事务处理，尤其是下订单的业务，多表的业务处理***例子看GenericServiceImpl
 * @author wmj
 * @version 2.0
 */
@Service
public class SysLogServiceImpl extends GenericServiceImpl<SysLog, Long> implements SysLogService {
	
	@Autowired
	private SysLogDao sysLogDao;
	
	
	@Autowired
	public void setGenericDao() {
		super.setGenericDao(sysLogDao);
	}

	public List<SysLog> findListByPage(Object parameter){
		return sysLogDao.findListByPage(parameter);
	}
	
	public List<SysLog> findListNewByPage(Object parameter) {
		return sysLogDao.findListNewByPage(parameter);
	}
	
	public Long deletes(Object parameter){
		return sysLogDao.deletes( parameter);
	}
	
	
}
