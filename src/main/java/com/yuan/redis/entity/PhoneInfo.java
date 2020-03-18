package com.yuan.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author yuan
 * @Date 2020/3/18 23:53
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneInfo {

    private Integer id;

    private String name;

    private Integer sales;
}
