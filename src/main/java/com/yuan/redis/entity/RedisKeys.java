package com.yuan.redis.entity;

/**
 * @Author yuan
 * @Date 2020/3/22 15:15
 * @Version 1.0
 */
public class RedisKeys {

    /**
     * 获取红包池子的key
     *
     * @param orderId
     * @return
     */
    public static String getHbPoolKey(Long orderId) {
        return "hb:pool:" + orderId;
    }

    /**
     * 获取红包领取用户记录key
     *
     * @param orderId
     * @return
     */
    public static String getHbRdkey(Long orderId) {
        return "hb:rd:" + orderId;
    }

    /**
     * 红包与用户信息列表key
     *
     * @param orderId
     * @return
     */
    public static String getDetailListKey(Long orderId) {
        return "hb:detailList:" + orderId;
    }


}
