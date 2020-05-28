package com.yuan.redis.interceptor;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 *
 */
@Aspect
@Slf4j
@Configuration
public class LoggerAOP {


    @Around(" @within(org.springframework.web.bind.annotation.RestController)")
    public Object interceptor(ProceedingJoinPoint pjp) throws Throwable {
        double begin = System.currentTimeMillis();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method1 = methodSignature.getMethod();
        log.info("=============请求开始=================");
        log.info("请求连接：{}", request.getRequestURL().toString());
        log.info("请求类型：{}", request.getMethod());
        log.info("请求方法：{}", signature.getDeclaringTypeName(), method1.getName());
        log.info("请求IP:{}", request.getRemoteAddr());
        String name = getName(pjp);
        String value = getValue(pjp);
        log.info("请求入参名称：{}", name);
        log.info("请求入参值： {}", value);
        Object proceed = pjp.proceed();
        double end = System.currentTimeMillis();
        log.info("请求耗时：{}秒", (end - begin) / 1000.0);
        log.info("请求返回：{}", JSON.toJSONString(proceed));
        log.info("=========请求结束==========");
        return proceed;
    }


    /**
     * 获取参数Map集合
     *
     * @param joinPoint
     * @return
     */
    public String getName(ProceedingJoinPoint joinPoint) {
        String[] paramNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
        StringBuilder sb = new StringBuilder();
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < paramNames.length; i++) {
            sb.append(paramNames[i] + "(" + args.getClass() + ")" + "  ");

        }
        return sb.toString();
    }

    /**
     * 获取参数Map集合
     *
     * @param joinPoint
     * @return
     */
    public String getValue(ProceedingJoinPoint joinPoint) {
        Object[] paramValues = joinPoint.getArgs();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < paramValues.length; i++) {
            sb.append(paramValues[i] + "  ");
        }
        return sb.toString();
    }




}
