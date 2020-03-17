package com.yuan.redis.enums;

import lombok.Getter;

/**
 * 用户点赞的状态
 *
 * @Author yuan
 * @Date 2020/3/15 22:49
 * @Version 1.0
 */
@Getter
public enum LinkedStatusEnum {
    LIKE(1, "点赞"),
    UNLIKE(0, "取消点赞/未点赞"),
    ;

    private Integer code;

    private String msg;

    LinkedStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
