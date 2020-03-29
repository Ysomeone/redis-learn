package com.yuan.redis.controller.admin.converter;


import com.yuan.redis.controller.admin.pojo.Department;
import com.yuan.redis.controller.admin.vo.DepartmentVO;
import org.springframework.beans.BeanUtils;


public class DepartmentConverter {


    /**
     * 转vo
     * @return
     */
    public static DepartmentVO converterToDepartmentVO(Department department){
        DepartmentVO departmentVO = new DepartmentVO();
        BeanUtils.copyProperties(department,departmentVO);
        return departmentVO;
    }
}
