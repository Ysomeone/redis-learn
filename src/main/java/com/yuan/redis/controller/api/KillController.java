package com.yuan.redis.controller.api;

import com.yuan.redis.authorization.CacheLock;
import com.yuan.redis.controller.api.common.Result;
import com.yuan.redis.service.KillService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author yuan
 * @Date 2020/3/31 22:23
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/kill")
public class KillController {
    @Resource
    private KillService killService;


    @CacheLock(prefix = "kill:robGoodsByAopLock:robGoods")
    @ApiOperation(value = "秒杀商品-通过aop利用redis分布式锁完成", httpMethod = "POST")
    @RequestMapping(value = "/bugGoodByAop", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "killActivityId", value = "活动id", dataType = "Long")
    })
    public Result<String> buyGoodByAop(Long userId, Long killActivityId) throws Exception {
        return killService.robGoodsByAopLock(userId, killActivityId);
    }

    @ApiOperation(value = "秒杀商品-通过RedissonLock分布式锁完成", httpMethod = "POST")
    @RequestMapping(value = "/robGoodsByRedissonLock.do", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "killActivityId", value = "活动id", dataType = "Long")
    })
    public Result<String> robGoodsByRedissonLock(Long userId, Long killActivityId) {
        return killService.robGoodsByRedissonLock(userId, killActivityId);
    }

}
