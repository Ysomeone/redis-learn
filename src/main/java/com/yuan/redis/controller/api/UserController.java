package com.yuan.redis.controller.api;

import com.yuan.redis.controller.common.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private RedisTemplate redisTemplate;


    @ApiOperation(value = "设置值", notes = "设置值")
    @RequestMapping(value = "/setValue.json", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "username", value = "用户名", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "password", value = "密码", required = true, dataType = "string"),
    })
    public Result<String> setValue(String username, String password) {
        redisTemplate.opsForValue().set(username, password);
        return Result.jsonStringOk(redisTemplate.opsForValue().get(username));
    }

}