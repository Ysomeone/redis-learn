package com.yuan.redis.service.impl;

import com.yuan.redis.entity.DynamicInfo;
import com.yuan.redis.entity.Phone;
import com.yuan.redis.entity.PhoneInfo;
import com.yuan.redis.service.PhoneService;
import com.yuan.redis.service.RedisService;
import com.yuan.redis.toolkit.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author yuan
 * @Date 2020/3/18 23:43
 * @Version 1.0
 */
@Service
public class PhoneServiceImpl implements PhoneService {

    @Resource
    private RedisTemplate redisTemplate;

    List<Phone> phones = Arrays.asList(
            new Phone(1, "苹果"),
            new Phone(2, "小米"),
            new Phone(3, "华为"),
            new Phone(4, "oppo"),
            new Phone(5, "vivo"));



    @Override
    public void buyPhone(int phoneId) {
        redisTemplate.opsForZSet().incrementScore(Constants.SALES_LIST, phoneId+"", 1);
        long currentTimeMillis = System.currentTimeMillis();
        String msg = currentTimeMillis + Constants.separator + phones.get(phoneId - 1).getName();
        redisTemplate.opsForList().leftPush(Constants.BUY_DYNAMIC, msg);
    }

    @Override
    public List<PhoneInfo> getPhList() {
        Set<ZSetOperations.TypedTuple<String>> set = redisTemplate.opsForZSet().rangeWithScores(Constants.SALES_LIST, 0, 4);
        ArrayList<PhoneInfo> list = new ArrayList<PhoneInfo>();
        for (ZSetOperations.TypedTuple s : set) {
            PhoneInfo phoneInfo = new PhoneInfo();
            int phoneId = Integer.parseInt(s.getValue().toString());
            phoneInfo.setName(phones.get(phoneId - 1).getName());
            phoneInfo.setSales(s.getScore().intValue());
            list.add(phoneInfo);
        }
        return list;
    }

    @Override
    public List<DynamicInfo> getBuyDynamic() {
        ArrayList<DynamicInfo> dynamicInfoList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            String result = (String) redisTemplate.opsForList().index(Constants.BUY_DYNAMIC, i);
            if (StringUtils.isEmpty(result)) {
                break;
            }
            String[] arr = result.split(Constants.separator);
            Long time = Long.valueOf(arr[0]);
            String phone = arr[1];
            DynamicInfo dynamicInfo = new DynamicInfo();
            dynamicInfo.setPhone(phone);
            dynamicInfo.setTime(new Date(time).toString());
            dynamicInfoList.add(dynamicInfo);
        }
        redisTemplate.opsForList().trim(Constants.BUY_DYNAMIC, 0, 19);
        return dynamicInfoList;
    }

    @Override
    public int phoneRank(int phoneId) {
        Long rank = redisTemplate.opsForZSet().reverseRank(Constants.SALES_LIST, String.valueOf(phoneId));
        return rank == null ? -1 : rank.intValue();
    }


    @Override
    public void clear(){
        redisTemplate.delete(Constants.SALES_LIST);
        redisTemplate.delete(Constants.BUY_DYNAMIC);
    }

    @Override
    public void initCache(){
        HashMap<String, Double> map = new HashMap<>();
        redisTemplate.opsForZSet().add(Constants.SALES_LIST,1,4);
        redisTemplate.opsForZSet().add(Constants.SALES_LIST,2,3);
        redisTemplate.opsForZSet().add(Constants.SALES_LIST,4,2);
    }

}
