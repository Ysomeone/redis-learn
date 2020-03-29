package com.yuan.redis.authorization;

import java.lang.annotation.*;

/**
 * @Author yuan
 * @Date 2020/3/29 16:18
 * @Version 1.0
 */
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CacheParam {

    /**
     * 字段名称
     *
     * @return String
     */
    String name() default "";
}
