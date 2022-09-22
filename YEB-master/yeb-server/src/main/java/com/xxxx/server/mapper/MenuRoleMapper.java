package com.xxxx.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxx.server.pojo.MenuRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LHC
 * @since 2021-06-25
 */
public interface MenuRoleMapper extends BaseMapper<MenuRole> {

    /**
     * 批量添加
     * @param rid
     * @param mids
     */
    Integer batchInset(@Param("rid") Integer rid, @Param("mids") List<Integer> mids);
}
