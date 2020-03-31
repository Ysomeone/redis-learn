package com.yuan.redis.controller.api.common;

/**
 * 状态码信息
 *
 * @author yuan
 */
public class ApiConstants {
    public static final String ok = "200000";
    /**
     * sessionId失效
     */
    public static final String SESSIONIDEXCEPTION = "200100";
    /**
     * 该活动不存在
     */
    public static final String ERROR100100 = "100100";
    /**
     * 您已经抢到该商品
     */
    public static final String ERROR100200 = "100200";

    /**
     * 该商品已经抢完
     */
    public static final String ERROR100300 = "100300";
    /**
     * 人数过多，请再次尝试！
     */
    public static final String ERROR100400 = "100400";
}
