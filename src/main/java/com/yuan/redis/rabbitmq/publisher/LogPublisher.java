package com.yuan.redis.rabbitmq.publisher;

import com.yuan.redis.controller.api.dto.UserDTO;
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
 * 系统日志记录-生产者
 *
 * @Author yuan
 * @Date 2020/4/21 23:14
 * @Version 1.0
 */
@Component
@Log4j2
public class LogPublisher {

    /**
     * 定义RabbitMQ操作组件
     */
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Environment env;



    public void sendLogMsg(UserDTO userDTO) {
        try {
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            rabbitTemplate.setExchange(env.getProperty("mq.login.exchange.name"));
            rabbitTemplate.setRoutingKey(env.getProperty("mq.login.routing.key.name"));
            rabbitTemplate.convertAndSend(userDTO, new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    MessageProperties messageProperties = message.getMessageProperties();
                    messageProperties.setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);
                    messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_KEY_CLASSID_FIELD_NAME, UserDTO.class);
                    return message;
                }
            });
            log.info("系统日志记录-生产者-发送登陆成功后的用户相关信息入队列-内容：{}",userDTO);
        } catch (Exception e) {
            log.error("系统日志记录-生产者-发送登陆成功后的用户相关信息入队列-发生异常：{}",userDTO);
        }
    }


}
