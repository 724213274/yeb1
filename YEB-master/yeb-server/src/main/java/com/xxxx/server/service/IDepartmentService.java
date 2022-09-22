package com.xxxx.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.server.pojo.Department;
import com.xxxx.server.pojo.Employee;
import com.xxxx.server.pojo.RespBean;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LHC
 * @since 2021-06-25
 */
public interface IDepartmentService extends IService<Department> {

    /**
     * 获取所有部门
     * @return
     */
    List<Department> getAllInFo();

    /**
     * 添加部门
     * @param department
     * @return
     */
    RespBean addDept(Department department);

    /**
     * 删除部门
     * @param id
     * @return
     */
    RespBean deleteDept(Integer id);


}
