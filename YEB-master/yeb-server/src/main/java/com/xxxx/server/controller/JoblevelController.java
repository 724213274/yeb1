package com.xxxx.server.controller;


import com.baomidou.mybatisplus.extension.api.R;
//import com.sun.org.apache.regexp.internal.RE;
import com.xxxx.server.pojo.Joblevel;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.service.IJoblevelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LHC
 * @since 2021-06-25
 */
@Api(value = "职称管理",tags = "职称管理")
@RestController
@RequestMapping("/system/basic/joblevel")
public class JoblevelController {

    @Autowired
    IJoblevelService joblevelService;

    @ApiOperation("职称列表")
    @GetMapping("/list")
    public List<Joblevel> getAllJoblevel(){
        List<Joblevel> list = joblevelService.list();
        return list;
    }

    @ApiOperation("添加职称")
    @PostMapping("/add")
    public RespBean addJoblevel(@RequestBody Joblevel joblevel){
        joblevel.setCreateDate(LocalDateTime.now());
        if (joblevelService.save(joblevel)){
            return RespBean.success("添加成功！");
        }
        return RespBean.error("添加失败！");
    }

    @ApiOperation("修改职称")
    @PutMapping("/update")
    public RespBean updateJoblevel(@RequestBody Joblevel joblevel){
        if (joblevelService.updateById(joblevel)){

            return RespBean.success("更新成功！");
        }
        return RespBean.error("更新失败！");
    }

    @ApiOperation("删除职称")
    @DeleteMapping("/delete")
    public RespBean deleteJoblevel(@RequestParam("id")Long id){

        joblevelService.removeById(id);
        return RespBean.success("删除成功！");
    }

    @ApiOperation("批量删除职称")
    @DeleteMapping("/delete/ids")
    public RespBean deleteByIds(@RequestParam("ids")List<Long> ids){
        joblevelService.removeByIds(ids);

        return RespBean.success("删除成功");
    }


}
