package com.yuan.redis.controller.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author yuan
 * @Date 2020/4/22 21:26
 * @Version 1.0
 */
@Data
public class UserDTO {
    /** ID */
    @ApiModelProperty(value = "ID", required = true)
    private Long id;

    /** 用户名称 */
    @ApiModelProperty(value = "用户名称", required = true)
    private String username;

    /** 用户密码 */
    @ApiModelProperty(value = "用户密码", required = true)
    private String password;
}
