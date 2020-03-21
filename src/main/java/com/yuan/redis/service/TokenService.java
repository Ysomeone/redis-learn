package com.yuan.redis.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author yuan
 * @Date 2020/3/21 0:51
 * @Version 1.0
 */
public interface TokenService {
    String createToken();

    boolean checkToken(HttpServletRequest request);
}
