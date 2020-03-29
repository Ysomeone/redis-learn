package com.yuan.redis.controller.admin.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuan.redis.controller.admin.bean.ActiveUser;
import com.yuan.redis.controller.admin.converter.MenuConverter;
import com.yuan.redis.controller.admin.converter.UserConverter;
import com.yuan.redis.controller.admin.mapper.*;
import com.yuan.redis.controller.admin.pojo.*;
import com.yuan.redis.controller.admin.service.UserService;
import com.yuan.redis.controller.admin.util.MD5Utils;
import com.yuan.redis.controller.admin.util.MenuTreeBuilder;
import com.yuan.redis.controller.admin.vo.MenuNodeVO;
import com.yuan.redis.controller.admin.vo.PageVO;
import com.yuan.redis.controller.admin.vo.UserEditVO;
import com.yuan.redis.controller.admin.vo.UserVO;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 *
 * @Date 2020/3/7 15:44
 * @Version 1.0
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 查询用户
     * @param name 用户名
     * @return
     */
    @Override
    public User findUserByName(String name) {
        User t = new User();
        t.setUsername(name);
        List<User> select = userMapper.select(t);
        if(!CollectionUtils.isEmpty(select)){
            return select.get(0);
        }else{
            return null;
        }
    }

    /**
     * 查询用户角色
     * @param id 用户ID
     * @return
     */
    @Override
    public List<Role> findRolesById(Long id) {
        List<Role> roles=new ArrayList<>();
        UserRole t = new UserRole();
        t.setUserId(id);
        List<UserRole> userRoleList = userRoleMapper.select(t);
        List<Long> rids=new ArrayList<>();
        if(!CollectionUtils.isEmpty(userRoleList)){
            for (UserRole userRole : userRoleList) {
                rids.add(userRole.getRoleId());
            }
            if(!CollectionUtils.isEmpty(rids)){
                for (Long rid : rids) {
                    Role role = roleMapper.selectByPrimaryKey(rid);
                    if(role!=null){
                        roles.add(role);
                    }
                }
            }
        }
        return roles;
    }

    /**
     * 查询用户的权限
     * @param roles 用户的角色
     * @return
     */
    @Override
    public List<Menu> findMenuById(List<Role> roles) {
        List<Menu> menus=new ArrayList<>();
        if(!CollectionUtils.isEmpty(roles)){
            Set<Long> menuIds=new HashSet<>();
            List<RoleMenu> roleMenus=new ArrayList<>();
            for (Role role : roles) {
                //根据角色ID查询权限ID
                RoleMenu t = new RoleMenu();
                t.setRoleId(role.getId());
                roleMenus= roleMenuMapper.select(t);
            }
            if(!CollectionUtils.isEmpty(roleMenus)){
                for (RoleMenu roleMenu : roleMenus) {
                    menuIds.add(roleMenu.getMenuId());
                }
            }
            if(!CollectionUtils.isEmpty(menuIds)){
                for (Long menuId : menuIds) {
                    //该用户所有的菜单
                    Menu menu = menuMapper.selectByPrimaryKey(menuId);
                    if(menu!=null){
                        menus.add(menu);
                    }
                }
            }
        }
        return menus;
    }

    /**
     * 获取菜单
     * @return
     */
    @Override
    public List<MenuNodeVO> findMenu() {
        List<Menu> menus;
        List<MenuNodeVO> menuNodeVOS=new ArrayList<>();
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        if(activeUser.getUser().getType()==0){
            //超级管理员
            menus=menuMapper.selectAll();
        }else {
            menus= activeUser.getMenus();
        }
        if(!CollectionUtils.isEmpty(menus)){
            menuNodeVOS= MenuConverter.converterToMenuNodeVO(menus);
        }
        //构建树形菜单
        return MenuTreeBuilder.build(menuNodeVOS);
    }

    /**
     * 用户列表
     * @param userVO
     * @return
     */
    @Override
    public PageVO<UserVO> findUserList(Integer pageNum, Integer pageSize, UserVO userVO) {
        //已经拥有的
        PageHelper.startPage(pageNum,pageSize);
        Example o = new Example(User.class);
        String username = userVO.getUsername();
        String nickname = userVO.getNickname();
        Integer sex = userVO.getSex();
        String email = userVO.getEmail();
        Example.Criteria criteria = o.createCriteria();
        if(username!=null&&!"".equals(username)){
            criteria.andLike("username","%"+username+"%");
        }
        if(nickname!=null&&!"".equals(nickname)){
            criteria.andLike("nickname","%"+nickname+"%");
        }
        if(email!=null&&!"".equals(email)){
            criteria.andLike("email","%"+email+"%");
        }
        if(sex!=null){
            criteria.andEqualTo("sex",sex);
        }

        criteria.andNotEqualTo("type",0);
        List<User> userList = userMapper.selectByExample(o);
        List<UserVO> userVOS = UserConverter.converterToUserVOList(userList);
        PageInfo<User> info=new PageInfo<>(userList);
        return new PageVO<>(info.getTotal(),userVOS);
    }

    /**
     * 删除用户
     * @param id 用户ID
     */
    @Override
    public void deleteById(Long id) {
        userMapper.deleteByPrimaryKey(id);
    }

    /**
     * 更新用户禁用状态
     * @param id
     * @param status
     */
    @Override
    public void updateStatus(Long id, Boolean status) {
        User t = new User();
        t.setId(id);
        t.setStatus(status?0:1);
        userMapper.updateByPrimaryKeySelective(t);
    }

    /**
     * 添加用户
     * @param userVO
     */
    @Override
    public void add(UserVO userVO) {
        User user = new User();
        BeanUtils.copyProperties(userVO,user);
        String salt=UUID.randomUUID().toString().substring(0,32);
        user.setPassword(MD5Utils.md5Encryption(user.getPassword(), salt));
        user.setModifiedTime(new Date());
        user.setCreateTime(new Date());
        user.setSalt(salt);
        user.setType(1);
        user.setStatus(1);
        user.setAvatar("http://badidol.com/uploads/images/avatars/201910/24/18_1571921832_HG9E55x9NY.jpg");
        userMapper.insert(user);
    }

    /**
     * 更新
     * @param id
     * @param userVO
     */
    @Override
    public void update(Long id, UserEditVO userVO) {
        User user = new User();
        BeanUtils.copyProperties(userVO,user);
        user.setModifiedTime(new Date());
        user.setId(id);
        userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 编辑
     * @param id
     * @return
     */
    @Override
    public UserEditVO edit(Long id) {
        User user = userMapper.selectByPrimaryKey(id);
        UserEditVO userEditVO = new UserEditVO();
        BeanUtils.copyProperties(user,userEditVO);
        return userEditVO;
    }

    /**
     * 用户拥有的角色ID
     * @param id 用户id
     * @return
     */
    @Override
    public List<Long> roles(Long id) {
        Example o = new Example(UserRole.class);
        o.createCriteria().andEqualTo("userId",id);
        List<UserRole> userRoleList = userRoleMapper.selectByExample(o);
        List<Long> roleIds=new ArrayList<>();
        if(!CollectionUtils.isEmpty(userRoleList)){
            for (UserRole userRole : userRoleList) {
                roleIds.add(userRole.getRoleId());
            }
        }
        return roleIds;
    }

    /**
     * 分配角色
     * @param id 用户id
     * @param rids 角色数组
     */
    @Override
    @Transactional
    public void assignRoles(Long id, Long[] rids) {
        //删除之前用户的所有角色
        Example o = new Example(UserRole.class);
        o.createCriteria().andEqualTo("userId",id);
        userRoleMapper.deleteByExample(o);
        //增加现在分配的
        if(rids.length>0){
            for (Long rid : rids) {
                UserRole userRole = new UserRole();
                userRole.setUserId(id);
                userRole.setRoleId(rid);
                userRoleMapper.insert(userRole);
            }
        }
    }
}
