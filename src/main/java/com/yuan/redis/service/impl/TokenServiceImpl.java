package com.yuan.redis.service.impl;

import com.yuan.redis.controller.api.common.ApiConstants;
import com.yuan.redis.controller.api.common.Constant;
import com.yuan.redis.controller.api.common.Result;
import com.yuan.redis.controller.api.exception.ApiException;
import com.yuan.redis.service.TokenService;
import com.yuan.redis.toolkit.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author yuan
 * @Date 2020/3/19 23:35
 * @Version 1.0
 */
@Service
public class TokenServiceImpl implements TokenService {

    private static final String TOKEN_NAME = "token";

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public String createToken() {
        String str = RandomUtil.UUID32();
        StringBuffer token = new StringBuffer();
        token.append(Constant.Redis.TOKEN_PREFIX).append(str);
        redisTemplate.opsForValue().set(token.toString(), token.toString(), Constant.Redis.EXPIRE_TIME_HOUR);
        return token.toString();
    }

    @Override
    public boolean checkToken(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_NAME);
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter(TOKEN_NAME);
            if (StringUtils.isEmpty(token)) {
                return false;
            }
        }
        if (!redisTemplate.hasKey(token)) {
            return false;
        }
        redisTemplate.delete(token);
        return true;
    }


}
