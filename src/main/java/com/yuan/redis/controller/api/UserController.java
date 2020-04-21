package com.yuan.redis.controller.api;

import com.yuan.redis.controller.api.common.ApiConstants;
import com.yuan.redis.controller.api.common.Result;
import com.yuan.redis.entity.Paramap;
import com.yuan.redis.entity.User;
import com.yuan.redis.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author yuan
 * @Date 2020/4/21 23:07
 * @Version 1.0
 */
@RequestMapping("/api/user/")
@RestController
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "/like.login", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "username", value = "用户名", dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "password", value = "密码", dataType = "string")
    })
    public Result<String> login(String username,String password){
        List<User> list = userService.findList(Paramap.create().put("username", username).put("password", password));
        if(CollectionUtils.isEmpty(list)){
            Result.jsonStringError("密码错误", ApiConstants.ERROR100100);
        }

        return Result.jsonStringOk();
    }


}


