package com.yuan.redis.toolkit.bloomFilter;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * @Author yuan
 * @Date 2020/5/3 14:03
 * @Version 1.0
 */
public class BloomFilterUttil {
    public static void main(String[] args) {
        int num=100000;
        /**
         * 默认误判率为3%
         */
        BloomFilter<Integer> filter = BloomFilter.create(Funnels.integerFunnel(), num);

        for(int i=0;i<num;i++){
            filter.put(i);
        }
        int count=0;
        /**
         * 那不存在的数据进行测试
         */
        for (int i = num; i < num + 10000; i++) {
            if(filter.mightContain(i)){
                count++;
            }
        }
        /**
         * 测试结果（一共误判了286个，误判率：0.0286）
         */
        System.out.println("一共误判了"+count);
        System.out.println("误判率："+(double)count/10000);
    }
}
