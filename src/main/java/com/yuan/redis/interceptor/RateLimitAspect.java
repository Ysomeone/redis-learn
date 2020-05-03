package com.yuan.redis.interceptor;

import com.google.common.util.concurrent.RateLimiter;
import com.yuan.redis.authorization.RateLimit;
import com.yuan.redis.controller.api.common.ApiConstants;
import com.yuan.redis.controller.api.exception.ApiException;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;


/**
 * @Author yuan
 * @Date 2020/5/3 15:00
 * @Version 1.0
 */
@Log4j2
@Aspect
@Configuration
public class RateLimitAspect {
    private RateLimiter rateLimiter = RateLimiter.create(10);

    /**
     * 带有指定注解切入
     */
    @Around("execution(public * *(..)) && @annotation(com.yuan.redis.authorization.RateLimit)")
    public Object interceptor(ProceedingJoinPoint pjp) throws Throwable {
        log.info("方法：" + pjp.getSignature().getName());
        log.info(pjp.getArgs());

        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        if (method.isAnnotationPresent(RateLimit.class)) {
            RateLimit rl = method.getAnnotation(RateLimit.class);
            rateLimiter.setRate(rl.perSecond());
            if (!rateLimiter.tryAcquire(rl.timeOut(), rl.timeOutUnit())) {
                throw new ApiException("访问人数太多,请稍后再试试", ApiConstants.ERROR100600);
            }
        }
        return pjp.proceed();
    }


}
