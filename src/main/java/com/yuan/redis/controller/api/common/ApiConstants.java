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

    /**
     * 您的手速过快，请休息下
     */
    public static final String ERROR100500 = "100500";

    /**
     * 您操作过于频繁，请稍后再试
     */
    public static final String ERROR100600 = "100600";


    /**
     *  网络异常请求稍后再试
     */
    public static final String ERROR100700 = "100700";

    /**
     * 该用户已经被注册
     */
    public static final String ERROR100800 = "100800";


}
