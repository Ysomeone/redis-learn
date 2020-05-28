package com.yuan.redis.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yuan.redis.entity.RedisKeys;
import com.yuan.redis.service.LuaScript;
import com.yuan.redis.service.RedpackService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author yuan
 * @Date 2020/3/22 15:19
 * @Version 1.0
 */
@Service
public class RedpackServiceImpl implements RedpackService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Resource
    private RedisTemplate redisTemplate;


    @Override
    public void genRedpack(long orderId, int redpackCount, int totalAmount) {
        Boolean flag = stringRedisTemplate.hasKey(RedisKeys.getHbPoolKey(orderId));
        if (!flag) {
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
            stringRedisTemplate.opsForList().leftPushAll(RedisKeys.getHbPoolKey(orderId), list);
        }

    }


    @Override
    public int[] doPartintionRedpack(int totalAmount, int redPackCount) {
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
                redpacks[i] = posArray[i] - posArray[i - 1] + 1;
            }
        }
        return redpacks;
    }


    @Override
    public String snatchRedpack(long userId, long orderId) {
        DefaultRedisScript<String> redisScript = new DefaultRedisScript<>();
        redisScript.setResultType(String.class);
        redisScript.setScriptText(LuaScript.getHbLua);
        List<String> keyList = new ArrayList();
        keyList.add(RedisKeys.getHbPoolKey(orderId));
        keyList.add(RedisKeys.getDetailListKey(orderId));
        keyList.add(RedisKeys.getHbRdKey(orderId));
        keyList.add(String.valueOf(userId));
        Object result = stringRedisTemplate.execute(redisScript, keyList);
        return (String) result;
    }

   @Override
    public String limit() {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setResultType(Long.class);
        redisScript.setScriptText(LuaScript.limit);
        List<String> keyList = new ArrayList();
        keyList.add("yuan");
        keyList.add("10");
        keyList.add("2");

       Long result = stringRedisTemplate.execute(redisScript, keyList);
        return result+"";
    }


    public static int getLegth(String str) {
        int length = str.length();
        int right = 0, left = 0;
        Set<Character> set = new HashSet<>();
        int maxSubStrLength = 0;
        for (int i = 0; i < str.length(); i++) {
            if (!set.contains(str.charAt(i))) {
                set.add(str.charAt(i));
                right++;
                maxSubStrLength = Math.max(maxSubStrLength, set.size());
            } else {
                set.remove(str.charAt(left));
            }
        }
        return maxSubStrLength;
    }

    public static void main(String[] args) {
        String yuan = "asdfasdfsdafasasdfghj";
        int legth = getLegth(yuan);
        System.out.println(legth);

    }


}
