package com.xxxx.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxx.server.pojo.Department;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LHC
 * @since 2021-06-25
 */
public interface DepartmentMapper extends BaseMapper<Department> {

    /**
     * 获取所有部门
     * @return
     */
    List<Department> getAllInFo(Integer parentId);

    /**
     * 添加部门
     * @param department
     * @return
     */
    void addDept(Department department);

    /**
     * 删除部门
     * @param department
     * @return
     */
    void deleteDept(Department department);
}
