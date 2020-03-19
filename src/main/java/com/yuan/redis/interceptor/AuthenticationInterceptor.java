package com.yuan.redis.interceptor;

import com.yuan.redis.controller.api.common.ApiConstants;
import com.yuan.redis.controller.api.exception.ApiException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * api拦截器
 *
 * @Author yuan
 * @Date 2020/3/18 23:10
 * @Version 1.0
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationInterceptor.class);


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        throw new ApiException(ApiConstants.SESSIONIDEXCEPTION, "sessionId异常");
        return true;
    }


}
