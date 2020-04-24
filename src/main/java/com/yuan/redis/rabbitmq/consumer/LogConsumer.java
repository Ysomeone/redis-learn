package com.yuan.redis.rabbitmq.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuan.redis.controller.api.dto.UserDTO;
import com.yuan.redis.entity.SysLog;
import com.yuan.redis.service.SysLogService;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author yuan
 * @Date 2020/4/22 21:37
 * @Version 1.0
 */
@Component
@Log4j2
public class LogConsumer {

    @Resource
    private SysLogService sysLogService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 监听消费并处理用户登陆后的消息
     */
    @RabbitListener(queues = "${mq.login.queue.name}", containerFactory = "singleListenerContainer")
    public void consumeMsg(@Payload UserDTO userDTO) {

        try {
            log.info("系统日志记录-消费者-监听消费用户登录成功后的消息-内容：{}", userDTO);
            SysLog sysLog = new SysLog();
            sysLog.setUserId(userDTO.getId());
            sysLog.setModule("用户登录模块");
            sysLog.setData(objectMapper.writeValueAsString(userDTO));
            sysLog.setMemo("用户登录成功记录相关登录信息");
            sysLog.setCreateTime(new Date());
            sysLogService.save(sysLog);
        } catch (Exception e) {
            log.error("系统日志记录-消费者-监听消费用户登录成功后的消息-发生异常：{} ", userDTO, e.fillInStackTrace());

        }

    }


}
