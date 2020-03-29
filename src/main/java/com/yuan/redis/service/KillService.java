package com.yuan.redis.service;

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
    Long robGoodsByAopLock(Long userId, Long killActivityId);

    /**
     * redisson 实现
     *
     * @param userId
     * @param killActivityId
     * @return
     */
    Long robGoodsByRedissonLock(Long userId, Long killActivityId);

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
    Long robGoodsByOptimismLock(Long userId, Long killActivityId);

}
