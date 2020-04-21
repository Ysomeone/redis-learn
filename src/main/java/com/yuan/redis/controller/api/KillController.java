package com.yuan.redis.controller.api;

import com.google.common.util.concurrent.RateLimiter;
import com.yuan.redis.authorization.CacheLock;
import com.yuan.redis.authorization.CacheParam;
import com.yuan.redis.controller.api.common.Result;
import com.yuan.redis.service.KillService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
    private static final Logger LOGGER = LoggerFactory.getLogger(KillController.class);

    //Guava令牌桶：每秒放行10个请求
    RateLimiter rateLimiter = RateLimiter.create(10);

    @ApiOperation(value = "秒杀商品-悲观锁", httpMethod = "POST")
    @RequestMapping(value = "/robGoodsByForUpdate", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "killActivityId", value = "活动id", dataType = "Long")
    })
    public Result<String> robGoodsByForUpdate(Long killActivityId) throws Exception {
        /**
         * 模拟用户id
         */
        int i = new Random().nextInt(500);
        long userId = i;
        return killService.robGoodsByForUpdate(userId, killActivityId);
    }


    @ApiOperation(value = "秒杀商品-乐观锁", httpMethod = "POST")
    @RequestMapping(value = "/robGoodsByOptimismLock", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "killActivityId", value = "活动id", dataType = "Long")
    })
    public Result<String> robGoodsByOptimismLock(Long killActivityId) throws Exception {
        LOGGER.info("等待时间" + rateLimiter.acquire());
        if (!rateLimiter.tryAcquire(1000, TimeUnit.MILLISECONDS)) {
            LOGGER.warn("你被限流了，真不幸，直接返回失败");
            return Result.jsonStringError("你被限流了，真不幸，直接返回失败", "232332");
        }
        /**
         * 模拟用户id
         */
        int i = new Random().nextInt(100);
        long userId = i;
        return killService.robGoodsByOptimismLock(userId, killActivityId);
    }

    @CacheLock(prefix = "kill:robGoodsByAopLock:robGoods")
    @ApiOperation(value = "秒杀商品-通过aop利用redis分布式锁完成", httpMethod = "POST")
    @RequestMapping(value = "/bugGoodByAop", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "killActivityId", value = "活动id", dataType = "Long")
    })
    public Result<String> buyGoodByAop(@CacheParam(name = "killActivityId") Long killActivityId) throws Exception {
        /**
         * 模拟用户id
         */
        int i = new Random().nextInt(100);
        long userId = (long) i;
        return killService.robGoodsByAopLock(userId, killActivityId);
    }

    @ApiOperation(value = "秒杀商品-通过RedissonLock分布式锁完成", httpMethod = "POST")
    @RequestMapping(value = "/robGoodsByRedissonLock", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "killActivityId", value = "活动id", dataType = "Long")
    })
    public Result<String> robGoodsByRedissonLock(Long killActivityId) {
        /**
         * 模拟用户id
         */
        int i = new Random().nextInt(100);
        long userId = i;





        return killService.robGoodsByRedissonLock(userId, killActivityId);
    }


}
