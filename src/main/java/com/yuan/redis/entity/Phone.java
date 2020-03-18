package com.yuan.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author yuan
 * @Date 2020/3/18 23:44
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Phone {

    private Integer id;

    private String name;

    private String ranking;
    public Phone(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
