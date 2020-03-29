package com.yuan.redis.controller.admin.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuan.redis.controller.admin.converter.RoleConverter;
import com.yuan.redis.controller.admin.mapper.MenuMapper;
import com.yuan.redis.controller.admin.mapper.RoleMapper;
import com.yuan.redis.controller.admin.mapper.RoleMenuMapper;
import com.yuan.redis.controller.admin.pojo.Role;
import com.yuan.redis.controller.admin.pojo.RoleMenu;
import com.yuan.redis.controller.admin.service.RoleService;
import com.yuan.redis.controller.admin.vo.PageVO;
import com.yuan.redis.controller.admin.vo.RoleVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @Date 2020/3/9 16:23
 * @Version 1.0
 **/
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 角色列表
     * @param pageNum
     * @param pageSize
     * @param roleVO
     * @return
     */
    @Override
    public PageVO<RoleVO> findRoleList(Integer pageNum, Integer pageSize, RoleVO roleVO) {
        PageHelper.startPage(pageNum,pageSize);
        Example o = new Example(Role.class);
        String roleName = roleVO.getRoleName();
        if (roleName!=null&&!"".equals(roleName)){
            o.createCriteria().andLike("roleName","%"+roleName+"%");
        }
        List<Role> roles = roleMapper.selectByExample(o);
        List<RoleVO> roleVOS= RoleConverter.converterToRoleVOList(roles);
        PageInfo<Role> info=new PageInfo<>(roles);
        return new PageVO<>(info.getTotal(),roleVOS);
    }

    /**
     * 添加角色
     * @param roleVO
     */
    @Override
    public void add(RoleVO roleVO) {
        Role role = new Role();
        BeanUtils.copyProperties(roleVO,role);
        role.setCreateTime(new Date());
        role.setModifiedTime(new Date());
        role.setStatus(1);
        roleMapper.insert(role);
    }

    /**
     * 删除角色
     * @param id
     */
    @Override
    public void deleteById(Long id) {
        roleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public RoleVO edit(Long id) {
        Role role = roleMapper.selectByPrimaryKey(id);
        RoleVO roleVO = new RoleVO();
        BeanUtils.copyProperties(role,roleVO);
        return roleVO;
    }

    @Override
    public void update(Long id, RoleVO roleVO) {
        Role role = new Role();
        BeanUtils.copyProperties(roleVO,role);
        role.setModifiedTime(new Date());
        roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public void updateStatus(Long id, Boolean status) {
        Role t = new Role();
        t.setId(id);
        t.setStatus(status?0:1);
        roleMapper.updateByPrimaryKeySelective(t);
    }

    @Override
    public List<Role> findAll() {
        return roleMapper.selectAll();
    }

    /**
     * 获取角色已有权限id
     * @param id
     * @return
     */
    @Override
    public List<Long> findMenuIdsByRoleId(Long id) {
        List<Long> ids=new ArrayList<>();
        Example o = new Example(RoleMenu.class);
        o.createCriteria().andEqualTo("roleId",id);
        List<RoleMenu> roleMenus = roleMenuMapper.selectByExample(o);
        if(!CollectionUtils.isEmpty(roleMenus)){
            for (RoleMenu roleMenu : roleMenus) {
                ids.add(roleMenu.getMenuId());
            }
        }
        return ids;
    }

}
