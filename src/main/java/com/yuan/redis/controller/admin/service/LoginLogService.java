package com.yuan.redis.controller.admin.service;


import com.yuan.redis.controller.admin.pojo.LoginLog;
import com.yuan.redis.controller.admin.vo.LoginLogVO;
import com.yuan.redis.controller.admin.vo.PageVO;

import java.util.List;

/**
 *
 * @Date 2020/3/20 19:10
 * @Version 1.0
 **/
public interface LoginLogService {

    /**
     * 添加登入日志
     * @param loginLog
     */
    void add(LoginLog loginLog);


    /**
     * 删除登入日志
     * @param id
     */
    void delete(Long id);


    /**
     * 登入日志列表
     * @param pageNum
     * @param pageSize
     * @param loginLogVO
     * @return
     */
    PageVO<LoginLogVO> findLoginLogList(Integer pageNum, Integer pageSize, LoginLogVO loginLogVO);

    /**
     * 批量删除登入日志
     * @param list
     */
    void batchDelete(List<Long> list);
}
