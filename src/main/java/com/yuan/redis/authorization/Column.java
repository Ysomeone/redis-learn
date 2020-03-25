package com.yuan.redis.authorization;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author yuan
 * @Date 2020/3/25 22:56
 * @Version 1.0
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    String name();

    boolean id() default false;

    boolean insertable() default true;

    boolean updatable() default true;
}
