package com.yuan.redis.controller.api;


import com.yuan.redis.controller.api.common.ApiConstants;
import com.yuan.redis.controller.api.common.Result;
import com.yuan.redis.entity.Paramap;
import com.yuan.redis.entity.Test;
import com.yuan.redis.entity.User;
import com.yuan.redis.service.AppUserService;
import com.yuan.redis.toolkit.RedissLockUtil;
import io.swagger.annotations.*;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * swagger 文档演示
 */
@RestController
@RequestMapping("/api/common")
@Api(value = "swagger演示", tags = "用来演示Swagger的一些注解")
public class TestController {
    @Resource
    private AppUserService userService;


    @ApiOperation(value = "修改用户密码", notes = "根据用户id修改密码", authorizations = {@Authorization("sessionId")})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "password", value = "旧密码", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "newPassword", value = "新密码", required = true, dataType = "String")
    })
    @RequestMapping(value = "/updatePassword.json", method = RequestMethod.POST)
    public Result<String>  updatePassword(HttpServletRequest request, String password,
                                 String newPassword) {
        return Result.jsonStringOk();
    }

    @ApiOperation(value = "保存用户信息", notes = "保存用户信息")
    @RequestMapping(value = "/test.json", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = 5000001, message = "参数错误")})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "username", value = "用户账号", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "password", value = "用户密码", required = true, dataType = "String")
    })
    public Result<Test>  saveUserInfo(String username,String password) {
        List<User> userList = userService.findList(Paramap.create().put("username", username));
        if(!CollectionUtils.isEmpty(userList)){
            return Result.jsonStringError("该用户已经被注册", ApiConstants.ERROR100800);
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        userService.save(user);
        return Result.jsonStringOk();
    }

    @ApiOperation(value = "测试限流", notes = "测试限流")
    @RequestMapping(value = "/testRateLimiter.json", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = 5000001, message = "参数错误")})
//    @RateLimit(perSecond =100, timeOut = 100)
    public Result<Test>  testRateLimiter(Test  test) {
        return Result.jsonStringOk();
    }


    @ApiOperation(value = "测试布隆过滤器", notes = "测试布隆过滤器")
    @RequestMapping(value = "/testBloomFilter.json", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = 5000001, message = "参数错误")})
    public Result<Test>  testBloomFilter() {
        User user = new User();
        user.setUsername("xx");
        return Result.jsonStringOk();
    }


}