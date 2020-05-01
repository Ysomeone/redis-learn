package com.yuan.redis.rabbitmq.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuan.redis.rabbitmq.entity.KnowledgeInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @Author yuan
 * @Date 2020/4/27 21:52
 * @Version 1.0
 */
@Component
@Log4j2
public class KnowledgeManualPublisher {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Environment env;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     *基于manual机制-生产者发送消息
     *
     * @param info
     */
    public void sendAutoMsg(KnowledgeInfo info) {
        try {
            if (info != null) {
                rabbitTemplate.setExchange(env.getProperty("mq.manual.knowledge.exchange.name"));
                rabbitTemplate.setRoutingKey(env.getProperty("mq.manual.knowledge.routing.key.name"));
                Message message = MessageBuilder.withBody(objectMapper.writeValueAsBytes(info)).setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
                rabbitTemplate.convertAndSend(message);
                log.info("基于MANUAL机制-生产者发送消息-内容为：{}",info);
            }
        } catch (Exception e) {
            log.error("基于MANUAL机制-生产者发送消息-发生异常：{}", info, e.fillInStackTrace());
        }

    }


}
