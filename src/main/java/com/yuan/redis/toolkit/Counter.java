package com.yuan.redis.toolkit;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ThreadFactory;

public class Counter {
    private int count = 0;

    public void increase() {
        getCount();
        ++count;
    }

    public int getCount() {
        return count;
    }

    public static int add(int a) {
        if (a == 1) {
            return 1;
        }
        System.out.println(a);
        return a + add(a - 1);
    }

    byte[] buffer = new byte[1 * 1024 * 1024];

    public void foo(long l, float f) {
        {
            int i = 0;
        }
        {
            String s = "hello ,world";
        }
    }
    public void load(int num,Object obj,long cc,float count,boolean flag,short[] arr){
        System.out.println(num);
        System.out.println(obj);
        System.out.println(count);
        System.out.println(flag);
        System.out.println(arr);
    }
    static String  base = "string";

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            String str = base + base;
            base = str;
            list.add(str.intern());

        }
    }
}
