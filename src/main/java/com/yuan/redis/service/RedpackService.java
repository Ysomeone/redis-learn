package com.yuan.redis.service;

/**
 * @Author yuan
 * @Date 2020/3/23 21:29
 * @Version 1.0
 */
public interface RedpackService {

    /**
     * 生成红包
     *
     * @param totalAmount  红包金额(分为单位)
     * @param orderId
     * @param redpackCount
     */
    void genRedpack(long orderId, int redpackCount, int totalAmount);

    /**
     * 划分红包
     *
     * @param totalAmount  红包总额 单位：分
     * @param redPackCount 红包数量
     * @return
     */
    int[] doPartintionRedpack(int totalAmount, int redPackCount);

    /**
     * 抢红包
     *
     * @param userId
     * @param orderId
     * @return
     */
    String snatchRedpack(long userId, long orderId);

     String limit() ;

}
