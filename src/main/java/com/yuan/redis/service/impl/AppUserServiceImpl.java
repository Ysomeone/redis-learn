package com.yuan.redis.service.impl;

import com.yuan.redis.dao.UserDao;
import com.yuan.redis.entity.MqOrder;
import com.yuan.redis.entity.User;
import com.yuan.redis.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * SERVICE - User(用户)
 *
 * @author
 * @version 2.0
 * @Transactional ***对表进行修改，添加时，记得加这个事务处理，尤其是下订单的业务，多表的业务处理***例子看GenericServiceImpl
 */
@Service
public class AppUserServiceImpl extends GenericServiceImpl<User, Long> implements AppUserService {

    @Autowired
    private UserDao userDao;

    private static ThreadLocal<MqOrder> objectThreadLocal = new ThreadLocal<>();


    @Autowired
    public void setGenericDao() {
        super.setGenericDao(userDao);
    }

    public List<User> findListByPage(Object parameter) {
        return userDao.findListByPage(parameter);
    }

    public List<User> findListNewByPage(Object parameter) {
        return userDao.findListNewByPage(parameter);
    }

    public Long deletes(Object parameter) {
        return userDao.deletes(parameter);
    }

    @PostConstruct
    public void init() {
        System.out.println("执行：——-------------------------------");
//        ExecutorService executorService = Executors.newFixedThreadPool(500);
//        for (int i = 0; i < 50000; i++) {
//            executorService.execute(() -> {
//                objectThreadLocal.set(new MqOrder());
//                Thread thread = Thread.currentThread();
//                System.out.println(Thread.currentThread().getName());
//            });
//        }
//        AtomicInteger atomicInteger = new AtomicInteger();
//        while (true) {
//            new Thread(() -> {
//                int num = atomicInteger.incrementAndGet();
//                System.out.println(num);
//
//                try {
//                    Thread.sleep(60*60*1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//            }).start();
//        }

    }

}
