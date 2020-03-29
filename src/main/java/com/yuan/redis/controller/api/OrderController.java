package com.yuan.redis.controller.api;

import com.google.common.util.concurrent.RateLimiter;
import com.yuan.redis.controller.api.common.Result;
import com.yuan.redis.entity.Paramap;
import com.yuan.redis.service.StockService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author yuan
 * @Date 2020/3/28 14:52
 * @Version 1.0
 */

@RestController
@RequestMapping("/api/order")
@Log
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Resource
    private StockService stockService;

    //Guava令牌桶：每秒放行10个请求
    RateLimiter rateLimiter = RateLimiter.create(10);

    /**
     * 导致超卖的错误示范
     *
     * @param sid
     * @return
     */
    @RequestMapping(value="/createWrongOrder", method = RequestMethod.POST)
    @ApiOperation(value = "导致超卖的错误示范", notes = "导致超卖的错误示范")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "sid", value = "库存id", dataType = "long")
    })
    public Result<String> createWrongOrder(Long sid) throws Exception{
        long id = 0L;
        id = stockService.createWrongOrder(sid);
//        LOGGER.info("创建订单id:[{}]", id);
        Paramap paramap = Paramap.create().put("id", id);
        return Result.jsonStringOk(paramap);
    }

    /**
     * 乐观锁更新库存 + 令牌桶限流
     *
     * @param sid
     * @return
     */
    @RequestMapping(value="/createOptimisticOrder", method = RequestMethod.POST)
    @ApiOperation(value = "乐观锁更新库存 + 令牌桶限流", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "sid", value = "库存id", dataType = "long")
    })
    public Result<String> createOptimisticOrder(Long sid) {
//        LOGGER.info("等待时间" + rateLimiter.acquire());
//        if (!rateLimiter.tryAcquire(1000, TimeUnit.MILLISECONDS)) {
//            LOGGER.warn("你被限流了，真不幸，直接返回失败");
//            return Result.jsonStringError("你被限流了，真不幸，直接返回失败", "232332");
//        }
        long id;
        try {
            id = stockService.createOptimisticOrder(sid);
//            LOGGER.error("购买成功，剩余库存为：[{}]", id);
        } catch (Exception e) {
            LOGGER.error("错误", e.getMessage());

            return Result.jsonStringError("购买失败，库存不足", "121111");
        }
        String message = String.format("购买成功，剩余库存为：%d", id);
        Paramap paramap = Paramap.create().put("message", message);
        return Result.jsonStringOk(paramap);
    }


    /**
     * 悲观锁更新库存：事务for update更新库存
     *
     * @param sid
     * @return
     */
    @RequestMapping(value="/createPessimisticOrder", method = RequestMethod.POST)
    @ApiOperation(value = "悲观锁更新库存：事务for update更新库存", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "sid", value = "库存id", dataType = "long")
    })
    public Result<String> createPessimisticOrder(Long sid) {
        long id;
        try {
            id = stockService.createPessimisticOrder(sid);
//            LOGGER.info("购买成功，剩余库存为：[{}]", id);
        } catch (Exception e) {
            return Result.jsonStringError("购买失败，库存不足", "121111");
        }
        String message = String.format("购买成功，剩余库存为：%d", id);
        Paramap paramap = Paramap.create().put("message", message);
        return Result.jsonStringOk(paramap);
    }


}
