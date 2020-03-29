package com.yuan.redis.controller.admin.vo;

import lombok.Data;


@Data
public class RoleTransferItemVO {
    private Long key;
    private String label;
    private boolean disabled;
}
