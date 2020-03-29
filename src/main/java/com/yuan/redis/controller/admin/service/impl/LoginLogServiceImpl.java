package com.yuan.redis.controller.admin.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuan.redis.controller.admin.mapper.LoginLogMapper;
import com.yuan.redis.controller.admin.pojo.LoginLog;
import com.yuan.redis.controller.admin.service.LoginLogService;
import com.yuan.redis.controller.admin.vo.LoginLogVO;
import com.yuan.redis.controller.admin.vo.PageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @Date 2020/3/20 19:11
 * @Version 1.0
 **/
@Service
public class LoginLogServiceImpl implements LoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;


    /**
     * 登入日志列表
     * @param pageNum
     * @param pageSize
     * @param loginLogVO
     * @return
     */
    @Override
    public PageVO<LoginLogVO> findLoginLogList(Integer pageNum, Integer pageSize, LoginLogVO loginLogVO) {
        PageHelper.startPage(pageNum,pageSize);
        Example o = new Example(LoginLog.class);
        o.setOrderByClause("login_time desc");
        if(loginLogVO.getLocation()!=null&&!"".equals(loginLogVO.getLocation())){
            o.createCriteria().andLike("location","%"+loginLogVO.getLocation()+"%");
        }
        if(loginLogVO.getIp()!=null&&!"".equals(loginLogVO.getIp())){
            o.createCriteria().andLike("ip","%"+loginLogVO.getIp()+"%");
        }
        if(loginLogVO.getUserName()!=null&&!"".equals(loginLogVO.getUserName())){
            o.createCriteria().andLike("userName","%"+loginLogVO.getUserName()+"%");
        }
        List<LoginLog> loginLogs = loginLogMapper.selectByExample(o);
        List<LoginLogVO> loginLogVOS=new ArrayList<>();
        if(!CollectionUtils.isEmpty(loginLogs)){
            for (LoginLog loginLog : loginLogs) {
                LoginLogVO logVO = new LoginLogVO();
                BeanUtils.copyProperties(loginLog,logVO);
                loginLogVOS.add(logVO);
            }
        }
        PageInfo<LoginLog> info=new PageInfo<>(loginLogs);
        return new PageVO<>(info.getTotal(),loginLogVOS);
    }

    @Override
    public void batchDelete(List<Long> list) {
        for (Long id : list) {
            delete(id);
        }
    }

    /**
     * 插入登入日志
     * @param loginLog
     */
    @Override
    public void add(LoginLog loginLog) {
        loginLogMapper.insert(loginLog);
    }

    /**
     * 删除登入日志
     * @param id
     */
    @Override
    public void delete(Long id) {
        loginLogMapper.deleteByPrimaryKey(id);
    }

}
