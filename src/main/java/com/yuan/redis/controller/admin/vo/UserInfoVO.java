package com.yuan.redis.controller.admin.vo;

import com.yuan.redis.controller.admin.pojo.Menu;
import com.yuan.redis.controller.admin.pojo.Role;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class UserInfoVO {

    private String username;

    private String avatar;

    private List<Menu> menus=new ArrayList<>();

    private List<Role> roles=new ArrayList<>();
}
