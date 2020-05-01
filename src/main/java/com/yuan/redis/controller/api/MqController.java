package com.yuan.redis.controller.api;

import com.yuan.redis.controller.api.common.Result;
import com.yuan.redis.service.MqOrderService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author yuan
 * @Date 2020/4/26 14:10
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/mq/")
public class MqController {

    @Resource
    private MqOrderService mqOrderService;

    @ApiOperation(value = "下单mq异步取消订单", httpMethod = "POST")
    @RequestMapping(value = "/mqToOrder", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户userId", dataType = "Long")
    })
    public Result<String> mqToOrder(Long userId) throws Exception {
        mqOrderService.saveOrder(userId);
        return Result.jsonStringOk();
    }


}
