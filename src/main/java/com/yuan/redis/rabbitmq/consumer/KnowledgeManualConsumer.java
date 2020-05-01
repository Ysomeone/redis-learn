package com.yuan.redis.rabbitmq.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.yuan.redis.rabbitmq.entity.KnowledgeInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 确认消息模式-人为手动确认消息
 *
 * @Author yuan
 * @Date 2020/4/27 22:01
 * @Version 1.0
 */
@Component
@Log4j2
public class KnowledgeManualConsumer implements ChannelAwareMessageListener {

    @Autowired
    private ObjectMapper objectMapper;


    /**
     * 监听消息消息
     *
     * @param message
     * @param channel
     */
    @Override
    public void onMessage(Message message, Channel channel) throws  Exception {
        MessageProperties messageProperties = message.getMessageProperties();
        long deliveryTag = messageProperties.getDeliveryTag();
        try {
            byte[] msg = message.getBody();
            KnowledgeInfo knowledgeInfo = objectMapper.readValue(msg, KnowledgeInfo.class);
            log.info("确认消费模式-人为手动确认消费-监听器监听消费消息-内容为：{} ",knowledgeInfo);
            channel.basicAck(deliveryTag, true);
        } catch (Exception e) {
            log.info("确认消费模式-人为手动确认消费-监听器监听消费消息-发生异常：",e.fillInStackTrace());
            channel.basicReject(deliveryTag,false);
        }
    }
}
