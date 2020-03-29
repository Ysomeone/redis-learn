package com.yuan.redis.authorization;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author yuan
 * @Date 2020/3/29 16:08
 * @Version 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CacheLock {

    /**
     * redis 锁key的前缀
     *
     * @return
     */
    String prefix() default "";

    /**
     * 过期秒杀，默认为5秒
     *
     * @return
     */
    int expire() default 5;

    /**
     * 超时时间单位
     *
     * @return 秒
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * key的分隔符（默认：）
     * 生成的key：N:SO1008:500
     *
     * @return
     */
    String delimiter() default ":";


}
