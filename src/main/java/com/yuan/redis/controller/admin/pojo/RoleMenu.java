package com.yuan.redis.controller.admin.pojo;

import lombok.Data;

import javax.persistence.Table;

@Data
@Table(name = "tb_role_menu")
public class RoleMenu {
    private Long roleId;

    private Long menuId;

}