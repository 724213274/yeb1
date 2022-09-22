package com.xxxx.server.controller;


import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.pojo.Salary;
import com.xxxx.server.service.ISalaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LHC
 * @since 2021-06-25
 */
@Api(value = "工资账套管理",tags = "工资账套管理")
@RestController
@RequestMapping("/salary/sob")
public class SalaryController {

    @Autowired
    private ISalaryService iSalaryService;


    @ApiOperation(value = "获取所有工资账套")
    @GetMapping("/get-All")
    public List<Salary> getAllSalary(){

        return iSalaryService.list();
    }

    @ApiOperation(value = "添加工资账套")
    @PostMapping("/add")
    public RespBean addSalary(
            @ApiParam @RequestBody Salary salary){
        salary.setCreateDate(LocalDateTime.now());
        if (iSalaryService.save(salary)){
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败！");
    }

    @ApiOperation(value = "删除工资账套")
    @DeleteMapping("/delete/{id}")
    public RespBean deleteById(
            @ApiParam @PathVariable Integer id){
        if (iSalaryService.removeById(id)){
            return RespBean.success("删除成功！");
        }
        return RespBean.error("删除失败!");
    }

    @ApiOperation(value = "修改工资账套")
    @PutMapping("/update")
    public RespBean updateSalary(
            @ApiParam @RequestBody Salary salary){

        if (iSalaryService.updateById(salary)){
            return RespBean.success("更新成功！");
        }
        return RespBean.error("更新失败！");
    }


}
