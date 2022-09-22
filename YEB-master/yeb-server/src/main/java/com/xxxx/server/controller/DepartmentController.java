package com.xxxx.server.controller;


import com.xxxx.server.pojo.Department;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.service.IDepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LHC
 * @since 2021-06-25
 */
@RestController
@RequestMapping("/system/basic/department")
@Api(value = "部门管理", tags = "部门管理")
public class DepartmentController {

    @Autowired
    IDepartmentService departmentService;

    @ApiOperation("获取所有部门")
    @GetMapping("/list")
    public List<Department> getAllInFo(){

        return departmentService.getAllInFo();
    }

    @ApiOperation("添加部门")
    @PostMapping("/add")
    public RespBean addDept(@RequestBody Department department){

        return departmentService.addDept(department);
    }

    @ApiOperation("删除部门")
    @DeleteMapping("/delete")
    public RespBean deleteDept(@RequestParam("id") Integer id){
        return departmentService.deleteDept(id);
    }
}
