package com.yuan.redis.test;

public class Singleton {

    private volatile String str = null;

    private Singleton() {
    }

    public String get() {
        if (str == null) {
            synchronized (Singleton.class) {
                if (str == null) {
                    str = "new Object";
                }
            }
        }
        return str;
    }
}
