package com.yuan.redis.toolkit.bloomFilter;

/**
 * @Author yuan
 * @Date 2020/5/3 16:34
 * @Version 1.0
 */
public  class Hash {
    /**
     * 二进制向量数组的大小
     */
     private int size;
    /**
     * 随机数种子
     */
    private int seed;

    public Hash(int cap,int seed){
        this.size=cap;
        this.seed=seed;
    }

    /**
     * 计算哈希值，也就是二进制向量计算位置
     */
     public int hash(String value){
         int result=0;
         int len=value.length();
         for (int i = 0; i < len; i++) {
             result=seed*result+value.charAt(i);
         }
         return (size-1)&result;
     }
}
