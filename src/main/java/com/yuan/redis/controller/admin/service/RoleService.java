package com.yuan.redis.controller.admin.service;


import com.yuan.redis.controller.admin.pojo.Role;
import com.yuan.redis.controller.admin.vo.PageVO;
import com.yuan.redis.controller.admin.vo.RoleVO;

import java.util.List;

/**
 *
 * @Date 2020/3/7 15:52
 * @Version 1.0
 **/
public interface RoleService {

    /**
     * 角色列表
     * @param pageNum
     * @param pageSize
     * @param roleVO
     * @return
     */
    PageVO<RoleVO> findRoleList(Integer pageNum, Integer pageSize, RoleVO roleVO);

    /**
     * 添加角色
     * @param roleVO
     */
    void add(RoleVO roleVO);

    /**
     * 删除角色
     * @param id
     */
    void deleteById(Long id);

    /**
     * 编辑角色
     * @param id
     * @return
     */
    RoleVO edit(Long id);

    /**
     * 更新角色
     * @param id
     * @param roleVO
     */
    void update(Long id, RoleVO roleVO);

    /**
     * 根据角色状态
     * @param id
     * @param status
     */
    void updateStatus(Long id, Boolean status);

    /**
     * 查询所有的角色
     * @return
     */
    List<Role> findAll();

    /**
     * 查询角色拥有的菜单权限id
     * @param id
     * @return
     */
    List<Long> findMenuIdsByRoleId(Long id);
}
