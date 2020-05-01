package com.yuan.redis.rabbitmq.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author yuan
 * @Date 2020/4/27 21:50
 * @Version 1.0
 */
@Data
@ToString
public class KnowledgeInfo  implements Serializable {

    private Integer id;

    private String code;

    private String mode;




}
