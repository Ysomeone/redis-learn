package com.yuan.redis.authorization;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @Author yuan
 * @Date 2020/5/3 14:51
 * @Version 1.0
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {


    /**
     * 每秒向桶放入令牌的数量
     */
     double perSecond() default Double.MAX_VALUE;

    /**
     * 获取令牌的等待时间
     *
     * @return
     */
    int timeOut() default 0;

    /**
     * 超时时间单位
     *
     * @return
     */
    TimeUnit timeOutUnit() default  TimeUnit.MILLISECONDS;

}
