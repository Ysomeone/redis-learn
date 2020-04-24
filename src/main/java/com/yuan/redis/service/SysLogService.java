package com.yuan.redis.service;

import com.yuan.redis.entity.SysLog;

import java.util.List;
/**
 * SERVICE - SysLog(日志记录表)
 * 
 * @author wmj
 * @version 2.0
 */
public interface SysLogService  extends GenericService<SysLog, Long> {
	public	List<SysLog> findListByPage(Object parameter);
	public	List<SysLog> findListNewByPage(Object parameter);
    public	 Long deletes(Object parameter);
}
