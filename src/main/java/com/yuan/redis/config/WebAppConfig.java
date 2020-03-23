package com.yuan.redis.config;

import com.yuan.redis.interceptor.AuthenticationInterceptor;
import com.yuan.redis.interceptor.AuthorizationInterceptor2;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author yuan
 * @Date 2020/3/18 23:15
 * @Version 1.0
 */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /**
         * 添加拦截器
         */
        registry.addInterceptor(new AuthenticationInterceptor())
                .addPathPatterns("/api/**");

        registry.addInterceptor(new AuthorizationInterceptor2())
                .addPathPatterns("/api/**");
    }
}
