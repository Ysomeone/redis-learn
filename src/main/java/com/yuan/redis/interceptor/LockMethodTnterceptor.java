package com.yuan.redis.interceptor;

import com.yuan.redis.authorization.CacheLock;
import com.yuan.redis.authorization.CacheParam;
import com.yuan.redis.controller.api.common.ApiConstants;
import com.yuan.redis.controller.api.exception.ApiException;
import com.yuan.redis.toolkit.Md5Utils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 通过aop利用redis分布式锁完成  锁的过期时间为1秒
 *
 * @Author yuan
 * @Date 2020/3/29 15:57
 * @Version 1.0
 */
@Aspect
@Slf4j
@Configuration
public class LockMethodTnterceptor {

    @Autowired
    private RedisTemplate redisTemplate;

    @Around("execution(public * *(..)) && @annotation(com.yuan.redis.authorization.CacheLock)")
    public Object interceptor(ProceedingJoinPoint pjp) throws ApiException {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        CacheLock lock = method.getAnnotation(CacheLock.class);
        if (StringUtils.isEmpty(lock.prefix())) {
            /**
             * 网络异常请求稍后再试
             */
            throw new ApiException(ApiConstants.ERROR100700, "人数过多，请再次尝试！");

        }
        String lockKey = getLockKey(pjp);
        Boolean success = redisTemplate.opsForValue().setIfAbsent(lockKey, "1");

        if (!success) {
            if (lock.prefix().equals("kill:robGoodsByAopLock:robGoods")) {
                /**
                 * 人数过多，请再次尝试
                 */
                throw new ApiException(ApiConstants.ERROR100400, "人数过多，请再次尝试！");
            } else {
                /**
                 * 您的手速过快，请休息下
                 */
                throw new ApiException(ApiConstants.ERROR100500, "您的手速过快，请休息下");
            }
        }
        if (lock.prefix().equals("kill:robGoodsByAopLock:robGoods")) {
            redisTemplate.expire(lockKey, 1, lock.timeUnit());
        } else {
            redisTemplate.expire(lockKey, lock.expire(), lock.timeUnit());
        }

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            /**
             * 您操作过于频繁，请稍后再试
             */
            throw new ApiException(ApiConstants.ERROR100600, "您操作过于频繁，请稍后再试");
        } finally {
            if (!lock.prefix().equals("kill:robGoodsByAopLock:robGoods")) {
                redisTemplate.delete(lockKey);
            }
        }

    }

    public static String getLockKey(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        CacheLock lockAnnotation = method.getAnnotation(CacheLock.class);
        final Object[] args = pjp.getArgs();
        final Parameter[] parameters = method.getParameters();
        StringBuilder builder = new StringBuilder();
        //TODO 默认解析方法里面带cacheParam 注解的属性，如果没有尝试解析实体对象中的
        for (int i = 0; i < parameters.length; i++) {
            final CacheParam annotation = parameters[i].getAnnotation(CacheParam.class);
            if (annotation == null) {
                continue;
            }
            builder.append(lockAnnotation.delimiter()).append(args[i]);
        }
        if (StringUtils.isEmpty(builder.toString())) {
            final Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            for (int i = 0; i < parameterAnnotations.length; i++) {
                final Object object = args[i];
                Field[] fields = object.getClass().getDeclaredFields();
                for (Field field : fields) {
                    final CacheParam annotation = field.getAnnotation(CacheParam.class);
                    if (annotation == null) {
                        continue;
                    }
                    field.setAccessible(true);
                    builder.append(lockAnnotation.delimiter()).append(ReflectionUtils.getField(field, object));
                }
            }

        }
        System.out.println("builder.toString()"+builder.toString());
        System.out.println("lockAnnotation.prefix()  Md5Utils.MD5(builder.toString())"+ lockAnnotation.prefix() + ":" + Md5Utils.MD5(builder.toString()));
        return lockAnnotation.prefix() + ":" + Md5Utils.MD5(builder.toString());
    }


}
