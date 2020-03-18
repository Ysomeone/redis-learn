package com.yuan.redis.controller.api;
import java.util.Date;

import com.yuan.redis.controller.api.common.Result;
import com.yuan.redis.entity.User;
import com.yuan.redis.entity.UserLike;
import com.yuan.redis.service.LikeRedisService;
import com.yuan.redis.service.RedisService;
import com.yuan.redis.service.UserLikeService;
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
@RequestMapping("/api/")
public class UserController {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private UserService userService;

    @Resource
    private RedisService redisService;

    @Resource
    private LikeRedisService likeRedisService;

    @Resource
    private UserLikeService userLikeService;

    @ApiOperation(value = "设置值", notes = "设置值")
    @RequestMapping(value = "/setValue.json", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "likedUserId", value = "用户名", dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "likedPostId", value = "密码", dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "status", value = "状态", dataType = "int")
    })
    public Result<String> setValue(String likedUserId, String likedPostId,Integer status) {
//        UserLike userLike = new UserLike();
//        userLike.setLikedUserId(likedUserId);
//        userLike.setLikedPostId(likedPostId);
//        userLike.setCreateTime(new Date());
//        userLike.setUpdateTime(new Date());
//        userLike.setStatus(status);
//        userLikeService.insert(userLike);
        likeRedisService.saveLike(likedUserId,likedPostId);
        return Result.jsonStringOk();
    }




}