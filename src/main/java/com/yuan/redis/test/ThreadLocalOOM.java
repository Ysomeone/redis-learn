package com.yuan.redis.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocalOOM {

    private static final int THREAD_LOOP_SIZE = 500;
    private static final int MOCK_BIG_DATA_LOOP_SIZE = 10000;

    private static ThreadLocal<List<User>> threadLocal = new ThreadLocal<>();

    private List<User> addBigList() {
        ArrayList<User> userList = new ArrayList<>(MOCK_BIG_DATA_LOOP_SIZE);
        for (int i = 0; i < MOCK_BIG_DATA_LOOP_SIZE; i++) {
            userList.add(new User("xuey1uan", "password" + i, "男", i));
        }
        return userList;
    }

    class User {
        private String userName;

        private String password;

        private String sex;

        private int age;

        public User(String userName, String password, String sex, int age) {
            this.userName = userName;
            this.password = password;
            this.sex = sex;
            this.age = age;
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_LOOP_SIZE);
        for(int i=0;i<THREAD_LOOP_SIZE;i++){
            executorService.execute(()->{
                threadLocal.set(new ThreadLocalOOM().addBigList());
                System.out.println(Thread.currentThread().getName());
//                threadLocal.remove();
            });
        }
        try{
            Thread.sleep(1000L);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

}
