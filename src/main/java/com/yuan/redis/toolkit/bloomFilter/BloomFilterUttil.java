package com.yuan.redis.toolkit.bloomFilter;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.google.common.util.concurrent.RateLimiter;

/**
 * @Author yuan
 * @Date 2020/5/3 14:03
 * @Version 1.0
 */
public class BloomFilterUttil {
    public static void main(String[] args) {
        /**
         * 总数量
         */
        int total=100000;
        BloomFilter<CharSequence> bf = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), total);
        /**
         * 初始化100000条数据到过滤器中
         */
        for(int i=0;i<total;i++){
            bf.put(""+i);
        }
        int count=0;
        for (int i = 0; i < total + 10000; i++) {
            if(bf.mightContain(""+i)){
                count++;
            }
        }
        System.out.println("已匹配数量"+count);


        RateLimiter limiter = RateLimiter.create(2);

        for(int i = 1; i < 10; i  ++) {
            double waitTime = limiter.acquire(i);
            System.out.println("cutTime=" + System.currentTimeMillis() + " acq:" + i + " waitTime:" + waitTime);
        }
    }
}
