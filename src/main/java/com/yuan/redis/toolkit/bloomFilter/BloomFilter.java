package com.yuan.redis.toolkit.bloomFilter;

import java.util.BitSet;

/**
 * @Author yuan
 * @Date 2020/5/3 16:29
 * @Version 1.0
 */
public class BloomFilter {
    /**
     * 二进制向量的位数，相当于能存储1000万条url左右，误报率为千万分之一
     */
    private static final int BIT_SIZE = 2 << 28;
    /**
     * 用于生成信息指纹的8个随机数，最好选取质数，hash8次生成8个位置在二进制向量
     */
    private static final int[] seeds = new int[]{3, 5, 7, 11, 13, 31, 37, 61};
    /**
     * 二进制向量大小
     */
    private BitSet bits = new BitSet(BIT_SIZE);
    /**
     * 用于存储8个随机哈希值对象
     */
    private Hash[] func = new Hash[seeds.length];

    /**
     * 构造的时候生成hash种子，对hash存储对象
     */
    public BloomFilter() {
        for (int i = 0; i < seeds.length; i++) {
            func[i] = new Hash(BIT_SIZE, seeds[i]);
        }
    }

    /**
     * 向过滤器中添加字符串
     *
     * @param value
     */
    public void addValue(String value) {
        /**
         * 将字符串value哈希为8个或多个整数，然后在这些整数的bit上变为1
         */
        if (value != null) {
            for (Hash f : func) {
                bits.set(f.hash(value), true);
            }
        }
    }

    /**
     * 判断字符串是否包含布隆过滤器中
     *
     * @param value
     * @return
     */
    public boolean contains(String value) {
        if (value == null) {
            return false;
        }
        boolean ret = true;
        /**
         * 将要比较的字符串计算hash值，再与布隆过滤器比对
         */
        for (Hash f : func) {
            ret = ret && bits.get(f.hash(value));
        }
        return ret;
    }
    public static void main(String[] args) {
        //向布隆过滤器中添加值
        BloomFilter b = new BloomFilter();
        b.addValue("jks");
        b.addValue("sdf");
        //判断是否存在
        System.out.println(b.contains("jks"));
        System.out.println(b.contains("as"));
    }

}
