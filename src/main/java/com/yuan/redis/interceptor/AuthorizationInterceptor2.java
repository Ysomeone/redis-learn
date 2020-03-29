package com.yuan.redis.interceptor;

import com.yuan.redis.authorization.Authorization;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Author yuan
 * @Date 2020/3/22 21:12
 * @Version 1.0
 */
public class AuthorizationInterceptor2 extends HandlerInterceptorAdapter {

    /**
     * Key的Request Key
     */
    public static final String REQUEST_CURRENT_KEY = "REQUEST_CURRENT_KEY";


    /**
     * 存放鉴权信息的Header名称，默认是Authorization
     */
    private String httpHeaderName = "Authorization";

    /**
     * 鉴权信息的无用前缀，默认为空
     */
    private String httpHeaderPrefix = "";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 如果不是映射到方法直接通过
         */
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        String token = request.getHeader(httpHeaderName);
        if (token != null && token.startsWith(httpHeaderPrefix) && token.length() > 0) {
            token = token.substring(httpHeaderPrefix.length());
            /**
             * 根据token拿key,如果不为空则返回true
             */
            String key = "";
            if (key != null) {
                request.setAttribute(REQUEST_CURRENT_KEY, "");
                return true;
            }
        }
        /**
         * 如果验证token失败，并且方法或类注明了Authorization,返回错误
         */
        if (method.getAnnotation(Authorization.class) != null || handlerMethod.getBeanType().getAnnotation(Authorization.class) != null) {
//            throw new ApiException(ApiConstants.SESSIONIDEXCEPTION, "sessionId异常");
        }
        request.setAttribute(REQUEST_CURRENT_KEY, null);
        return true;
    }


}
