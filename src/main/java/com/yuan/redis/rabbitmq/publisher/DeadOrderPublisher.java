package com.yuan.redis.rabbitmq.publisher;

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
 * @Date 2020/4/24 22:30
 * @Version 1.0
 */
@Component
@Log4j2
public class DeadOrderPublisher {

    @Autowired
    private Environment env;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送用户下单记录id的消息入死信队列
     * @param orderId
     */
    public void sendMsg(Integer orderId){
        try {
            //设置消息的传输格式-Json格式
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            //设置基本交换机
            rabbitTemplate.setExchange(env.getProperty("mq.producer.order.exchange.name"));
            //设置基本路由
            rabbitTemplate.setRoutingKey(env.getProperty("mq.producer.order.routing.key.name"));

            //发送对象类型的消息
            rabbitTemplate.convertAndSend(orderId, new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    //获取消息属性对象
                    MessageProperties messageProperties=message.getMessageProperties();
                    //设置消息的持久化策略
                    messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    //设置消息头-即直接指定发送的消息所属的对象类型
                    messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,Integer.class);
                    //返回消息实例
                    return message;
                }
            });
            //打印日志
            log.info("用户下单支付超时-发送用户下单记录id的消息入死信队列-内容为：orderId={} ",orderId);

        }catch (Exception e){
            log.error("用户下单支付超时-发送用户下单记录id的消息入死信队列-发生异常：orderId={} ",orderId,e.fillInStackTrace());
        }
    }


}
