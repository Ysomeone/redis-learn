package com.yuan.redis.controller.admin.controller;


import com.yuan.redis.controller.admin.bean.ResponseBean;
import com.yuan.redis.controller.admin.service.DepartmentService;
import com.yuan.redis.controller.admin.vo.DeanVO;
import com.yuan.redis.controller.admin.vo.DepartmentVO;
import com.yuan.redis.controller.admin.vo.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = "系统部门接口")
@RestController
@RequestMapping("/admin/department")
public class DepartmentController {


    @Autowired
    private DepartmentService departmentService;

    /**
     * 部门列表
     * @return
     */
    @ApiOperation(value = "部门列表",notes = "部门列表,根据部门名模糊查询")
    @GetMapping("/findDepartmentList")
    public ResponseBean findDepartmentList(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                           @RequestParam(value = "pageSize") Integer pageSize,
                                           DepartmentVO departmentVO){
        PageVO<DepartmentVO> departmentsList= departmentService.findDepartmentList(pageNum,pageSize,departmentVO);
        return ResponseBean.success(departmentsList);
    }

    /**
     * 查找部门主任
     * @return
     */
    @ApiOperation(value = "部门主任",notes = "查找部门主任,排除掉已经禁用的用户")
    @GetMapping("/findDeanList")
    public ResponseBean findDeanList(){
        List<DeanVO> managerList=departmentService.findDeanList();
        return ResponseBean.success(managerList);
    }

    /**
     * 添加部门
     * @return
     */
    @RequiresPermissions({"department:delete"})
    @ApiOperation(value = "添加部门")
    @PostMapping("/add")
    public ResponseBean add(@RequestBody @Validated DepartmentVO departmentVO){
        try {
            departmentService.add(departmentVO);
            return ResponseBean.success("添加部门成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBean.error("添加部门失败");
        }
    }

    /**
     * 编辑部门
     * @param id
     * @return
     */
    @ApiOperation(value = "编辑部门")
    @RequiresPermissions({"department:edit"})
    @GetMapping("/edit/{id}")
    public ResponseBean edit(@PathVariable Long id){
        try {
            DepartmentVO departmentVO=departmentService.edit(id);
            return ResponseBean.success(departmentVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBean.error("编辑部门失败");
        }
    }

    /**
     * 更新部门
     * @return
     */
    @ApiOperation(value = "更新部门")
    @RequiresPermissions({"department:update"})
    @PostMapping("/update/{id}")
    public ResponseBean update(@PathVariable Long id, @RequestBody @Validated DepartmentVO departmentVO){
        try {
            departmentService.update(id,departmentVO);
            return ResponseBean.success("更新部门成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBean.error("更新部门失败");
        }
    }

    /**
     * 删除部门
     * @param id
     * @return
     */
    @ApiOperation(value = "删除部门")
    @RequiresPermissions({"department:delete"})
    @DeleteMapping("/delete/{id}")
    public ResponseBean delete(@PathVariable Long id){
        try {
            departmentService.delete(id);
            return ResponseBean.success("删除部门成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBean.error("删除部门失败");
        }
    }


}
