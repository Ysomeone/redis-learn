package com.yuan.redis.dao;


import com.yuan.redis.entity.SysLog;

import java.util.List;

/**
 * DAO - SysLog(日志记录表)
 * 
 * @author wmj
 * @version 2.0
 */
public interface SysLogDao  extends GenericDao<SysLog, Long> {
	List<SysLog> findListByPage(Object parameter);
	List<SysLog> findListNewByPage(Object parameter);
    Long deletes(Object parameter);
}
