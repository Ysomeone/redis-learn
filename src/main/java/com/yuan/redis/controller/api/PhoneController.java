package com.yuan.redis.controller.api;

import com.yuan.redis.controller.api.common.Result;
import com.yuan.redis.entity.DynamicInfo;
import com.yuan.redis.entity.Paramap;
import com.yuan.redis.entity.Phone;
import com.yuan.redis.entity.PhoneInfo;
import com.yuan.redis.service.PhoneService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @Author yuan
 * @Date 2020/3/19 22:44
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/phone")
public class PhoneController {
    List<Phone> phones = Arrays.asList(
            new Phone(1, "苹果"),
            new Phone(2, "小米"),
            new Phone(3, "华为"),
            new Phone(4, "oppo"),
            new Phone(5, "vivo"));

    @Resource
    private PhoneService phoneService;


    @RequestMapping(value = "/home.json", method = RequestMethod.POST)
    @ApiOperation(value = "查看数据", notes = "查看数据")
    public Result<String> home() {
        for (Phone phone : phones) {
            int ranking = phoneService.phoneRank(phone.getId()) + 1;
            phone.setRanking(ranking == 0 ? "榜上无名" : "销量排名" + ranking);
        }
        List<PhoneInfo> phList = phoneService.getPhList();
        List<DynamicInfo> buyDynamic = phoneService.getBuyDynamic();
        Paramap paramap = Paramap.create().put("phList", phList);
        paramap.put("buyDynamic", buyDynamic);
        return Result.jsonStringOk(paramap);
    }

    @RequestMapping(value = "/buy", method = RequestMethod.POST)
    @ApiOperation(value = "购买手机", notes = "购买手机")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "phoneId", value = "手机id", dataType = "int")
    })
    public Result<String> buyPhone(int phoneId) {
        phoneService.buyPhone(phoneId);
        return Result.jsonStringOk();
    }

    @RequestMapping(value="/ranking", method = RequestMethod.POST)
    @ApiOperation(value = "获得手机排名", notes = "获得手机排名")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "phoneId", value = "手机id", dataType = "int")
    })
    public Result<String> phoneRanking(int phoneId) {
        int i = phoneService.phoneRank(phoneId);
        return Result.jsonStringOk(i);
    }

    @RequestMapping(value="/clear", method = RequestMethod.POST)
    @ApiOperation(value = "清空缓存", notes = "清空缓存")
    public Result<String> clear() {
        phoneService.clear();
        return Result.jsonStringOk();
    }

}
