package com.yuan.redis.config;

import com.yuan.redis.rabbitmq.consumer.KnowledgeManualConsumer;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.HashMap;

/**
 * RabbitMQ自定义注入Bean配置
 *
 * @Author yuan
 * @Date 2020/4/20 22:58
 * @Version 1.0
 */
@Configuration
@Log4j2
public class RabbitmqConfig {

    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Autowired
    private SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;

    @Autowired
    private Environment env;

    /**
     * 单一消费者
     *
     * @return
     */
    @Bean(name = "singleListenerContainer")
    public SimpleRabbitListenerContainerFactory listenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(1);
        factory.setPrefetchCount(1);
        return factory;
    }

    /**
     * 多个消费者
     *
     * @return
     */
    @Bean(name = "multiListenerContainer")
    public SimpleRabbitListenerContainerFactory multiListenerContainer() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factoryConfigurer.configure(factory, connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setAcknowledgeMode(AcknowledgeMode.NONE);
        factory.setConcurrentConsumers(10);
        factory.setMaxConcurrentConsumers(15);
        factory.setPrefetchCount(10);
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                log.info("消息发送成功:conrrelationData({}),ack({}),cause({})", correlationData, ack, cause);
            }
        });
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                log.info("消息丢失：exchange({}),route({}),replyCode({}),replyText({}),message:({})", exchange, routingKey, replyCode, replyText, message);
            }
        });
        return rabbitTemplate;
    }


    /**
     * 创建队列
     *
     * @return
     */
    @Bean(name = "basicQueue")
    public Queue basicQueue() {
        return new Queue(env.getProperty("mq.basic.info.queue.name"), true);
    }

    /**
     * 创建交换机
     *
     * @return
     */
    @Bean
    public DirectExchange basicExchange() {
        return new DirectExchange(env.getProperty("mq.basic.info.exchange.name"), true, false);
    }

    /**
     * 创建绑定
     */
    @Bean
    public Binding basicBinding() {
        return BindingBuilder.bind(basicQueue()).to(basicExchange()).with(env.getProperty("mq.basic.info.routing.key.name"));
    }

    /**
     * 创建简单消息模型-对象类型：队列 、交换机和路由
     **/
    @Bean(name = "objectQueue")
    public Queue objectQueue() {
        return new Queue(env.getProperty("mq.object.info.queue.name"), true);
    }

    @Bean
    public DirectExchange objectExchage() {
        return new DirectExchange(env.getProperty("mq.object.info.exchange.name"), true, false);
    }

    @Bean
    public Binding objectBinding() {
        return BindingBuilder.bind(objectQueue()).to(objectExchage()).with(env.getProperty("mq.object.info.routing.key.name"));
    }


    /**
     * 创建消息模型
     */
//    @Bean(name="fanoutQueueOne")
//    public Queue fanoutQueueOne() {
//        return new Queue(env.getProperty("mq.fanout.queue.one.name"), true);
//    }
//
//    @Bean(name="fanoutQueueTwo")
//    public Queue fanoutQueueTwo(){
//        return new Queue(env.getProperty("mq.fanout.queue.two.name"),true);
//    }
//
//    @Bean
//    public FanoutExchange fanoutExchange(){
//        return new FanoutExchange(env.getProperty("mq.fanout.exchange.name"),true,false);
//    }
    @Bean(name = "loginQueue")
    public Queue loginQueue() {
        return new Queue(env.getProperty("mq.login.queue.name"), true);
    }

    @Bean
    public TopicExchange loginExchange() {
        return new TopicExchange(env.getProperty("mq.login.exchange.name"), true, false);
    }

    @Bean
    public Binding loginBinding() {
        return BindingBuilder.bind(loginQueue()).to(loginExchange()).with(env.getProperty("mq.login.routing.key.name"));
    }


    /**
     * 用户下单支付超时-RabbitMQ 死信队列消息模型构建
     */

    @Bean
    public Queue orderDeadQueue() {
        HashMap<String, Object> hashMap = new HashMap();
        hashMap.put("x-dead-letter-exchange", env.getProperty("mq.order.dead.exchange.name"));
        hashMap.put("x-dead-letter-routing-key", env.getProperty("mq.order.dead.routing.key.name"));
        /**
         * 设定TTL,单位为ms，在这里为了方便测试，设置为10s
         */
        hashMap.put("x-message-ttl", 10000);
        return new Queue(env.getProperty("mq.order.dead.queue.name"), true, false, false, hashMap);

    }

    //创建“基本消息模型”的基本交换机 - 面向生产者
    @Bean
    public TopicExchange orderProducerExchange() {
        return new TopicExchange(env.getProperty("mq.producer.order.exchange.name"), true, false);
    }

    //创建“基本消息模型”的基本绑定-基本交换机+基本路由 - 面向生产者
    @Bean
    public Binding orderProducerBinding() {
        return BindingBuilder.bind(orderDeadQueue()).to(orderProducerExchange()).with(env.getProperty("mq.producer.order.routing.key.name"));
    }


    //创建真正队列 - 面向消费者
    @Bean
    public Queue realOrderConsumerQueue() {
        return new Queue(env.getProperty("mq.consumer.order.real.queue.name"), true);
    }

    //创建死信交换机
    @Bean
    public TopicExchange basicOrderDeadExchange() {
        return new TopicExchange(env.getProperty("mq.order.dead.exchange.name"), true, false);
    }

    //创建死信路由及其绑定
    @Bean
    public Binding basicOrderDeadBinding() {
        return BindingBuilder.bind(realOrderConsumerQueue()).to(basicOrderDeadExchange()).with(env.getProperty("mq.order.dead.routing.key.name"));
    }



    /**
     * 单一消费者-确认模式为MANUAL
     * @return
     */

    //创建队列
    @Bean(name = "manualQueue")
    public Queue manualQueue(){
        return new Queue(env.getProperty("mq.manual.knowledge.queue.name"),true);
    }
    //创建交换机
    @Bean
    public TopicExchange manualExchange(){
        return new TopicExchange(env.getProperty("mq.manual.knowledge.exchange.name"),true,false);
    }
    //创建绑定
    @Bean
    public Binding manualBinding(){
        return BindingBuilder.bind(manualQueue()).to(manualExchange()).with(env.getProperty("mq.manual.knowledge.routing.key.name"));
    }
    //定义手动确认消费模式对应的消费者实例
    @Autowired
    private KnowledgeManualConsumer knowledgeManualConsumer;

    /**
     * 创建单一消费者-确认模式为manual-并指定监听的队列和消费者
     */
    @Bean(name = "simpleContainerManual")
    public SimpleMessageListenerContainer simpleContainer(@Qualifier("manualQueue") Queue manualQueue) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setConcurrentConsumers(1);
        container.setMaxConcurrentConsumers(1);
        container.setPrefetchCount(1);

        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setQueues(manualQueue);
        container.setMessageListener(knowledgeManualConsumer);
        return container;
    }


}
