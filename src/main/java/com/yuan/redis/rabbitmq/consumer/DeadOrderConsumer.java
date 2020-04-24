package com.yuan.redis.rabbitmq.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @Author yuan
 * @Date 2020/4/24 22:40
 * @Version 1.0
 */
@Component
@Log4j2
public class DeadOrderConsumer {

    @Autowired
    private ObjectMapper objectMapper;


    /**
     * 用户下单支付超时-处理服务实例
     *
     * @param orderId
     */
    @RabbitListener(queues = "${mq.consumer.order.real.queue.name}", containerFactory = "singleListenerContainer")
    public void consumerMsg(@Payload Integer orderId) {
        try {
            log.info("用户下单支付超时消息模型-监听真正队列-监听到消息内容为：orderId={}", orderId);

        } catch (Exception e) {
            log.error("用户下单支付超时消息模型-监听真正队列-发生异常：orderId={}", orderId, e.fillInStackTrace());
        }


    }


}
