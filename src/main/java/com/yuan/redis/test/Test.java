package com.yuan.redis.test;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;

public class Test {
    private static String te = "s";

    class a {
        private int size = 12;

        public void setSize(int t) {
            this.size = t;
            System.out.println(te);
        }
    }

    public static int get(int a, int b) {
        int big = a > b ? a : b;
        int small = a > b ? b : a;
        if (big % small == 0) {
            return small;
        }
      return get(big%small,small);
    }


    public static void main(String[] args) {


//        ThreadLocal<String> stringThreadLocal = new ThreadLocal<>();
//        stringThreadLocal.set("yuan");
//        String s = stringThreadLocal.get();
//        System.out.println("s:"+s);
//        HashMap<String, String> stringStringHashMap  = new HashMap<String, String>();
//        ConcurrentHashMap<String, String> stringStringConcurrentHashMap = new ConcurrentHashMap<>();
//        stringStringConcurrentHashMap.put("1","1");
//        new ReentrantLock();
        Integer i1 = 100;
        Integer i2 = 100;
        Integer i3 = 200;
        Integer i4 = 200;
        int i = Integer.parseInt("12123");
        String s = "asdfasdf11";
        System.out.println(s.charAt(2));
        String[] a = {"f", "sfd"};
        String as = "asdf;adsfa;sdf";
        String as1 = ";asdf;adsfa;sdf; ";
        String[] split = as.split(";");
        String[] split1 = as1.split(";");
        Object o1 = new Object();
        String join = String.join(",", a);
        System.out.println("join:" + join);
        as1.getBytes();
        System.out.println(i);
        System.out.println(i1 == i2);
        System.out.println(i3 == i4);
        new StringBuilder();
        ArrayList<String> asList = new ArrayList<String>();
        asList.add("dsf");
        asList.add("d32sf");
        asList.add("d23sf");
        String[] atg = new String[asList.size()];
        asList.toArray(atg);
        for (String o : atg) {
            System.out.println(o);
        }
        new StringBuffer();
        Executors.newFixedThreadPool(10);
    }
}
