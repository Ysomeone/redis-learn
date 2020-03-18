package com.yuan.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author yuan
 * @Date 2020/3/19 0:02
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DynamicInfo {
    private String phone;
    private String time;
}
