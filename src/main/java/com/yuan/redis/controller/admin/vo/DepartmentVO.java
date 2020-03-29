package com.yuan.redis.controller.admin.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 *
 * @Date 2020/3/15 14:13
 * @Version 1.0
 **/
@Data
public class DepartmentVO {

    private Long id;

    @NotBlank(message = "院系名称不能为空")
    private String name;

    @NotBlank(message = "办公电话不能为空")
    private String phone;

    @NotBlank(message = "办公地址不能为空")
    private String address;

    @NotNull(message = "系主任不能为空")
    private Long mgrId;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date createTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date modifiedTime;

    /** 系主任的名字*/
    private String mgrName;

}
