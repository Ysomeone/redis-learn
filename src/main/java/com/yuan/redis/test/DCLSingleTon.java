package com.yuan.redis.test;


public class DCLSingleTon {
    private DCLSingleTon(){
        System.out.println(Thread.currentThread().getName() +" 构造方法执行了");
    }

    private static DCLSingleTon singleTon = null;

    public static DCLSingleTon getInstance(){
        //加锁前后都做一次判断
        if (singleTon == null){
            synchronized (DCLSingleTon.class){
                if (singleTon ==null){
                    singleTon = new DCLSingleTon();
                }
            }
        }
        return singleTon;
    }

    public static void main(String[] args) {
        //多线程环境下,执行多次,构造方法只执行一次;说明只有一个对象
        for(int i = 1;i<8000;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    DCLSingleTon.getInstance();
                }
            }).start();
        }
    }
}
