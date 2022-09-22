package com.xxxx.server.controller;


import com.xxxx.server.pojo.Employee;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.pojo.RespPageBean;
import com.xxxx.server.pojo.Salary;
import com.xxxx.server.service.IEmployeeService;
import com.xxxx.server.service.ISalaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
@RequestMapping("/salary-adjust")
@Api(value = "员工工资账套管理",tags = "员工工资账套管理")
public class SalaryAdjustController {

    @Autowired
    private ISalaryService iSalaryService;
    @Autowired
    private IEmployeeService iEmployeeService;


    @ApiOperation(value = "获取所有工资账套")
    @GetMapping("/get-All")
    public List<Salary> getAllSalary(){

        return iSalaryService.list();
    }

    @ApiOperation(value = "获取所有员工工资标准")
    @GetMapping("/list")
    public RespPageBean getAllEmpSalary(
            @ApiParam @RequestParam(defaultValue = "1") Integer currentPage,
            @ApiParam @RequestParam(defaultValue = "10") Integer pageSize){
        // 还没写结果集映射和SQL
        return iEmployeeService.getAllEmpSalary(currentPage,pageSize);
    }

    @ApiOperation(value = "修改员工工资账套")
    @PutMapping("/update")
    public RespBean updateEmpSalary(
            @ApiParam @RequestParam Integer eid,
            @ApiParam @RequestParam Integer eSid){
        if (iEmployeeService.updateEmpEsid(eid, eSid)){
            return RespBean.success("修改成功");
        }
        return RespBean.error("修改失败，该员工不存在");
    }

}
