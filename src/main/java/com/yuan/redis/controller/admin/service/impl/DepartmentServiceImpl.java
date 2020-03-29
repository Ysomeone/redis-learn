package com.yuan.redis.controller.admin.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuan.redis.controller.admin.converter.DepartmentConverter;
import com.yuan.redis.controller.admin.enums.BizUserTypeEnum;
import com.yuan.redis.controller.admin.mapper.DepartmentMapper;
import com.yuan.redis.controller.admin.mapper.RoleMapper;
import com.yuan.redis.controller.admin.mapper.UserMapper;
import com.yuan.redis.controller.admin.mapper.UserRoleMapper;
import com.yuan.redis.controller.admin.pojo.Department;
import com.yuan.redis.controller.admin.pojo.Role;
import com.yuan.redis.controller.admin.pojo.User;
import com.yuan.redis.controller.admin.pojo.UserRole;
import com.yuan.redis.controller.admin.service.DepartmentService;
import com.yuan.redis.controller.admin.vo.DeanVO;
import com.yuan.redis.controller.admin.vo.DepartmentVO;
import com.yuan.redis.controller.admin.vo.PageVO;
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
 * @Date 2020/3/15 14:15
 * @Version 1.0
 **/
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 系别列表
     *
     * @param pageNum
     * @param pageSize
     * @param departmentVO
     * @return
     */
    @Override
    public PageVO<DepartmentVO> findDepartmentList(Integer pageNum, Integer pageSize, DepartmentVO departmentVO) {
        PageHelper.startPage(pageNum, pageSize);
        Example o = new Example(Department.class);
        if (departmentVO.getName() != null && !"".equals(departmentVO.getName())) {
            o.createCriteria().andLike("name", "%" + departmentVO.getName() + "%");
        }
        List<Department> departments = departmentMapper.selectByExample(o);
        //转vo
        List<DepartmentVO> departmentVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(departments)) {
            for (Department department : departments) {
                DepartmentVO d = new DepartmentVO();
                BeanUtils.copyProperties(department, d);
                User user = userMapper.selectByPrimaryKey(d.getMgrId());
                d.setMgrName(user.getUsername());
                departmentVOS.add(d);
            }
        }
        PageInfo<Department> info = new PageInfo<>(departments);
        return new PageVO<>(info.getTotal(), departmentVOS);
    }

    /**
     * 查找所有系主任
     *
     * @return
     */
    @Override
    public List<DeanVO> findDeanList() {
        Example o = new Example(Role.class);
        o.createCriteria().andEqualTo("roleName", BizUserTypeEnum.DEAN.getVal());
        List<Role> roles = roleMapper.selectByExample(o);
        List<DeanVO> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(roles)) {
            Role role = roles.get(0);
            Example o1 = new Example(UserRole.class);
            o1.createCriteria().andEqualTo("roleId", role.getId());
            List<UserRole> userRoleList = userRoleMapper.selectByExample(o1);
            if (!CollectionUtils.isEmpty(userRoleList)) {
                //存放所有系主任的id
                List<Long> userIds = new ArrayList<>();
                for (UserRole userRole : userRoleList) {
                    userIds.add(userRole.getUserId());
                }
                if(userIds.size()>0){
                    for (Long userId : userIds) {
                        User user = userMapper.selectByPrimaryKey(userId);
                        //所有可用的
                        if(user!=null&&user.getStatus()==1){
                            DeanVO deanVO = new DeanVO();
                            deanVO.setName(user.getUsername());
                            deanVO.setId(user.getId());
                            list.add(deanVO);
                        }
                    }
                }
            }
        }
        return list;
    }

    /**
     * 添加院系
     * @param departmentVO
     */
    @Override
    public void add(DepartmentVO departmentVO) {
        Department department = new Department();
        BeanUtils.copyProperties(departmentVO,department);
        department.setCreateTime(new Date());
        department.setModifiedTime(new Date());
        departmentMapper.insert(department);
    }

    /**
     * 编辑院系
     * @param id
     * @return
     */
    @Override
    public DepartmentVO edit(Long id) {
        Department department = departmentMapper.selectByPrimaryKey(id);
        DepartmentVO departmentVO = DepartmentConverter.converterToDepartmentVO(department);
        return departmentVO;
    }

    /**
     * 更新院系
     * @param id
     * @param departmentVO
     */
    @Override
    public void update(Long id, DepartmentVO departmentVO) {
        Department department = new Department();
        BeanUtils.copyProperties(departmentVO,department);
        department.setId(id);
        department.setModifiedTime(new Date());
        departmentMapper.updateByPrimaryKeySelective(department);
    }

    @Override
    public void delete(Long id) {
        departmentMapper.deleteByPrimaryKey(id);
    }
}
