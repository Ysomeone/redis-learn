package com.yuan.redis.controller.api;

import com.yuan.redis.authorization.Authorization;
import com.yuan.redis.controller.api.common.Result;
import com.yuan.redis.entity.Paramap;
import com.yuan.redis.service.LikeRedisService;
import com.yuan.redis.service.RedpackService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author yuan
 */
@RestController
@RequestMapping("/api/user")
public class RedisController {



    @Resource
    private LikeRedisService likeRedisService;

    @Resource
    private RedpackService redpackService;

    @ApiOperation(value = "点赞", notes = "点赞")
    @RequestMapping(value = "/like.json", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "likedUserId", value = "点赞人id", dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "likedPostId", value = "被点赞人id", dataType = "string")
    })
    @Authorization
    public Result<String> like(@Param(value = "likedUserId") String likedUserId, @Param(value = "likedPostId") String likedPostId) {
        likeRedisService.saveLike(likedUserId, likedPostId);
        return Result.jsonStringOk();
    }

    @ApiOperation(value = "测试抢红包", notes = "测试抢红包")
    @RequestMapping(value = "/testRedPack.json", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "orderId", value = "订单id", dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "repackCount", value = "红包数量", dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "totalAmount", value = "钱包金额", dataType = "int")
    })
    public Result<String> testRedPack(Long orderId,int repackCount,int totalAmount) throws Exception {
//        redpackService.genRedpack(orderId, 5,totalAmount);
//        IdWorker idWorker = new IdWorker();
//        int N = 100;
//        CyclicBarrier barrier = new CyclicBarrier(N);
//        for (int i = 0; i < N; i++) {
//            new Thread(() -> {
//                long userId = idWorker.nextId();
//                try {
//                    System.out.println("用户" + userId + "准备抢红包");
//                    barrier.await();
//                } catch (2 e) {
//                    e.printStackTrace();
//                } catch (BrokenBarrierException e) {
//                    e.printStackTrace();
//                }
//                String result = redpackService.snatchRedpack(userId, orderId);
//                if ("0".equals(result)) {
//                    System.out.println("用户" + userId + "未抢到红包，原因：红包已领完");
//                } else if ("1".equals(result)) {
//                    System.out.println("用户" + userId + "未抢到红包，原因：红包已领过");
//                } else {
//                    System.out.println("用户" + userId + "抢到红包：" + result);
//                }
//            }, "thread" + i).start();
//        }
//        Thread.sleep(Integer.MAX_VALUE);
        String limit = redpackService.limit();
        return Result.jsonStringOk(Paramap.create().put("limit",limit));
    }


}