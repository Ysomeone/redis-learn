package com.yuan.redis.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yuan.redis.entity.RedisKeys;
import com.yuan.redis.service.LuaScript;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @Author yuan
 * @Date 2020/3/22 15:19
 * @Version 1.0
 */
//@Service
public class RedpackServiceImpl {

    @Resource
    private StringRedisTemplate redisTemplate;


    public void genRedpack(long orderId, int redpackCount) {
        Boolean flag = redisTemplate.hasKey(RedisKeys.getHbPoolKey(orderId));
        if (!flag) {
            /**
             * 红包金额(分为单位)
             */
            int totalAmount = 2000;
            int[] redpacks = doPartintionRedpack(totalAmount, redpackCount);
            String[] list = new String[redpacks.length];
            for (int i = 0; i < redpacks.length; i++) {
                JSONObject jsonObject = new JSONObject();
                /**
                 * 红包Id,红包金额，存的是分
                 */
                jsonObject.put("hbId", i + 1);
                jsonObject.put("amount", redpacks[i]);
                list[i] = jsonObject.toJSONString();
            }
            redisTemplate.opsForList().leftPushAll(RedisKeys.getHbPoolKey(orderId), list);
        }

    }


    /**
     * 划分红包
     *
     * @param totalAmount  红包总额 单位：分
     * @param redPackCount 红包数量
     * @return
     */
    private int[] doPartintionRedpack(int totalAmount, int redPackCount) {
        Random random = new Random();
        /**
         * 每个人至少分到一分钱,如果有2000分，6人，随机得到五个小于1994（2000-6）的数
         * 比如 a1=4，a2=120，a3=324，a4=500，a5=700(随机拿到的五个数进行排序)，那么红包钱分别为： a1+1,a2-a1+1,a3-a2+1,a4-a3+1,a5-a4+1,1994-a5+1(总和刚好为2000)
         */
        int randomMax = totalAmount - redPackCount;
        int[] posArray = new int[redPackCount - 1];
        for (int i = 0; i < posArray.length; i++) {
            int pos = random.nextInt(randomMax);
            posArray[i] = pos;
        }
        Arrays.sort(posArray);
        int[] redpacks = new int[redPackCount];
        for (int i = 0; i <= posArray.length; i++) {
            if (i == 0) {
                redpacks[i] = posArray[i] + 1;
            } else if (i == posArray.length) {
                redpacks[i] = randomMax - posArray[i - 1] + 1;
            } else {
                redpacks[i] = posArray[i] - posArray[i + 1] + 1;
            }
        }
        return redpacks;
    }

    public String snatchRedpack(long userId, long orderId) {
//        Object object = jedisUtils.eval(LuaScript.getHbLua,4,
//                RedisKeys.getHbPoolKey(orderId),//
//                RedisKeys.getDetailListKey(orderId),//
//                RedisKeys.getHbRdKey(orderId),String.valueOf(userId));

//        return (String) object;
//        List<String> keyList = new ArrayList();
//        keyList.add("count");
//        keyList.add("rate.limiting:127.0.0.1");
//        redisTemplate.execute(LuaScript.getHbLua,);
        return "";
    }


}
