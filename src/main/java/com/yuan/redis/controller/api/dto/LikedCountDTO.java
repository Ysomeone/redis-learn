package com.yuan.redis.controller.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author yuan
 * @Date 2020/3/17 22:02
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikedCountDTO {
    private String id;

    private Integer count;

}
