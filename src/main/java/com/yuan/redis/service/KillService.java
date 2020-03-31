package com.yuan.redis.service;

import com.yuan.redis.controller.api.common.Result;

/**
 * SERVICE - KillOrder(秒杀)
 *
 * @author
 * @version 2.0
 */
public interface KillService {

    /**
     * aop 加redis 实现
     *
     * @param userId
     * @param killActivityId
     * @return
     */
    Result<String> robGoodsByAopLock(Long userId, Long killActivityId) throws Exception;

    /**
     * redisson 实现
     *
     * @param userId
     * @param killActivityId
     * @return
     */
    Result<String> robGoodsByRedissonLock(Long userId, Long killActivityId);

    /**
     * redis队列实现
     *
     * @param userId
     * @param killActivityId
     * @return
     */
    Long robGoodsByRedisPush(Long userId, Long killActivityId);

    /**
     * 乐观锁实现
     *
     * @param userId
     * @param killActivityId
     * @return
     */
    Result<String> robGoodsByOptimismLock(Long userId, Long killActivityId);

}
