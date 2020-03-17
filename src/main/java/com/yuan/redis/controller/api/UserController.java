package com.yuan.redis.controller.api;

import com.yuan.redis.controller.api.common.Result;
import com.yuan.redis.entity.User;
import com.yuan.redis.service.RedisService;
import com.yuan.redis.service.UserService;
import com.yuan.redis.toolkit.StringUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author yuan
 */
@RestController
public class UserController {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private UserService userService;

    @Resource
    private RedisService redisService;


    @ApiOperation(value = "设置值", notes = "设置值")
    @RequestMapping(value = "/setValue.json", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "username", value = "用户名", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "password", value = "密码", required = true, dataType = "string"),
    })
    public Result<String> setValue(String username, String password) {
        return Result.jsonStringOk();
    }


}