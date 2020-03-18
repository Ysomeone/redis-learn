package com.yuan.redis.entity;

import com.yuan.redis.enums.LinkedStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author yuan
 * @Date 2020/3/17 21:50
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLike implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String likedUserId;

    private String likedPostId;

    private Date createTime;

    private Date updateTime;

    private Integer status = LinkedStatusEnum.UNLIKE.getCode();

    public UserLike(String likedUserId, String likedPostId, Integer status) {
        this.likedUserId = likedUserId;
        this.likedPostId = likedPostId;
        this.status = status;
    }

}
