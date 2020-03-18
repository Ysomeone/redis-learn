package com.yuan.redis.service.impl;

import com.yuan.redis.entity.Phone;
import com.yuan.redis.entity.PhoneInfo;
import com.yuan.redis.service.RedisService;
import com.yuan.redis.toolkit.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @Author yuan
 * @Date 2020/3/18 23:43
 * @Version 1.0
 */
@Service
public class PhoneServiceImpl {

    @Resource
    private RedisTemplate redisTemplate;

    List<Phone> phones = Arrays.asList(
            new Phone(1, "苹果"),
            new Phone(2, "小米"),
            new Phone(3, "华为"),
            new Phone(4, "oppo"),
            new Phone(5, "vivo"));


    public void buyPhone(int phoneId) {
        redisTemplate.opsForZSet().intersectAndStore(Constants.SALES_LIST, 1, String.valueOf(phoneId));
        long currentTimeMillis = System.currentTimeMillis();
        String msg = currentTimeMillis + Constants.separator + phones.get(phoneId - 1).getName();
        redisTemplate.opsForList().leftPush(Constants.BUY_DYNAMIC, msg);
    }

    public List<PhoneInfo> getPhList() {
        Set<RedisZSetCommands.Tuple> set = redisTemplate.opsForZSet().rangeByScore(Constants.SALES_LIST, 0, 4);
        ArrayList<PhoneInfo> list = new ArrayList<PhoneInfo>();
        for (RedisZSetCommands.Tuple s : set) {
            PhoneInfo phoneInfo = new PhoneInfo();
            int phoneId = Integer.parseInt(s.getValue().toString());
            phoneInfo.setName(phones.get(phoneId - 1).getName());
            phoneInfo.setSales(s.getScore().intValue());
            list.add(phoneInfo);
        }
        return list;
    }


}
