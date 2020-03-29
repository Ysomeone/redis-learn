package com.yuan.redis.service.impl;

import com.yuan.redis.dao.ActivityGoodsDao;
import com.yuan.redis.dao.KillOrderDao;
import com.yuan.redis.service.KillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service
public class KillServiceImpl implements KillService {
	
	@Autowired
	private KillOrderDao killOrderDao;

	@Autowired
	private ActivityGoodsDao activityGoodsDao;

	@Override
	public Long robGoodsByAopLock(Long userId, Long killActivityId) {
		return null;
	}

	@Override
	public Long robGoodsByRedissonLock(Long userId, Long killActivityId) {
		return null;
	}

	@Override
	public Long robGoodsByRedisPush(Long userId, Long killActivityId) {
		return null;
	}

	@Override
	public Long robGoodsByOptimismLock(Long userId, Long killActivityId) {
		return null;
	}
}
