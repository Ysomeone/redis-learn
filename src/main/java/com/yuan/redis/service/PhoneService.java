package com.yuan.redis.service;

import com.yuan.redis.entity.DynamicInfo;
import com.yuan.redis.entity.PhoneInfo;

import java.util.List;

/**
 * @Author yuan
 * @Date 2020/3/19 22:39
 * @Version 1.0
 */
public interface PhoneService {

    void buyPhone(int phoneId);

    List<PhoneInfo> getPhList();

    List<DynamicInfo> getBuyDynamic();

    int phoneRank(int phoneId);

    void clear();

    void initCache();

}