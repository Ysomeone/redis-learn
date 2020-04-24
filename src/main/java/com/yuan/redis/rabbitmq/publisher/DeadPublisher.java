package com.yuan.redis.rabbitmq.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuan.redis.controller.api.dto.DeadInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @Author yuan
 * @Date 2020/4/23 22:37
 * @Version 1.0
 */
@Component
@Log4j2
public class DeadPublisher {

    @Autowired
    private Environment env;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendMsg(DeadInfo info) {
        try {
            /**
             * 设置消息的传输格式-Json格式
             */
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());

            rabbitTemplate.setExchange(env.getProperty("mq.producer.basic.exchange.name"));

            rabbitTemplate.setRoutingKey(env.getProperty("mq.producer.basic.routing.key.name"));

            rabbitTemplate.convertAndSend(info, new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    /**
                     * 获取消息属性对象
                     */
                    MessageProperties messageProperties = message.getMessageProperties();
                    /**
                     * 设置消息的持久化策略
                     */
                    messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    /**
                     *设置消息头-即直接指定发送的消息所属的对象类型
                     */
                    messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, DeadInfo.class);
                    /**
                     * 设置消息的ttl-当消息和队列同时都设置了ttl时，则取较短时间的值
                     */
                    messageProperties.setExpiration(String.valueOf(10000));

                    return message;
                }
            });
            log.info("死信队列实战-发送对象类型的消息入死信队列-内容为：{}", info);
        } catch (Exception e) {
            log.error("死信队列实战-发送对象类型的消息入死信队列-发生异常：{}", info, e.fillInStackTrace());
        }


    }

}
