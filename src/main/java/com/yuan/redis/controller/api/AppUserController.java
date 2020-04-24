package com.yuan.redis.controller.api;

import com.yuan.redis.controller.api.common.ApiConstants;
import com.yuan.redis.controller.api.common.Result;
import com.yuan.redis.controller.api.dto.UserDTO;
import com.yuan.redis.entity.Paramap;
import com.yuan.redis.entity.User;
import com.yuan.redis.rabbitmq.publisher.LogPublisher;
import com.yuan.redis.service.AppUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AppUserController {

    @Resource
    private AppUserService userService;

    @Autowired
    private LogPublisher logPublisher;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "username", value = "用户名", dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "password", value = "密码", dataType = "string")
    })
    @ApiOperation(value = "登陆以mq异步插入日志", notes = "登陆以mq异步插入日志")
    public Result<String> login(String username,String password){
        List<User> list = userService.findList(Paramap.create().put("username", username).put("password", password));
        if(CollectionUtils.isEmpty(list)){
            Result.jsonStringError("密码错误", ApiConstants.ERROR100100);
        }else{
            User user = list.get(0);
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(user,userDTO);
            logPublisher.sendLogMsg(userDTO);
        }

        return Result.jsonStringOk();
    }


    @RequestMapping(value = "/buy", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "username", value = "用户名", dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "password", value = "密码", dataType = "string")
    })
    @ApiOperation(value = "死信队列取消超时订单", notes = "死信队列取消超时订单")
    public Result<String> buy(Long userId){


        return Result.jsonStringOk();
    }







}


