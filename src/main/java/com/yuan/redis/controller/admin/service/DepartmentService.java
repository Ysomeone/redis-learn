package com.yuan.redis.controller.admin.service;



import com.yuan.redis.controller.admin.vo.DeanVO;
import com.yuan.redis.controller.admin.vo.DepartmentVO;
import com.yuan.redis.controller.admin.vo.PageVO;

import java.util.List;

/**
 *
 * @Date 2020/3/15 14:12
 * @Version 1.0
 **/
public interface DepartmentService {
    /**
     * 部门列表
     * @param pageNum
     * @param pageSize
     * @param departmentVO
     * @return
     */
    PageVO<DepartmentVO> findDepartmentList(Integer pageNum, Integer pageSize, DepartmentVO departmentVO);

    /**
     * 查询所有部门主任
     * @return
     */
    List<DeanVO> findDeanList();

    /**
     * 添加院部门
     * @param departmentVO
     */
    void add(DepartmentVO departmentVO);

    /**
     * 编辑院部门
     * @param id
     * @return
     */
    DepartmentVO edit(Long id);

    /**
     * 更新院部门
     * @param id
     * @param departmentVO
     */
    void update(Long id, DepartmentVO departmentVO);

    /**
     * 删除院部门
     * @param id
     */
    void delete(Long id);
}