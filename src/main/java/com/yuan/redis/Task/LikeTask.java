package com.yuan.redis.Task;

import com.yuan.redis.service.LikedService;
import jdk.nashorn.internal.runtime.logging.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;

/**
 * @Author yuan
 * @Date 2020/3/17 22:12
 * @Version 1.0
 */
@Logger
public class LikeTask extends QuartzJobBean {

    @Autowired
    private LikedService likedService;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
/**
 *         将 Redis 里的点赞信息同步到数据库里
 */
        likedService.transLikedFromRedis2DB();
        likedService.transLikedCountFromRedis2DB();
    }
}
