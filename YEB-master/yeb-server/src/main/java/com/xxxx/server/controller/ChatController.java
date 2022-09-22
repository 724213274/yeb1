package com.xxxx.server.controller;

//import com.sun.org.apache.regexp.internal.RE;
import com.xxxx.server.pojo.Admin;
import com.xxxx.server.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.jsqlparser.expression.operators.relational.RegExpMySQLOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @program: yeb
 * @description: 和谁聊天
 * @author: Honors
 * @create: 2021-08-02 14:12
 */
@RestController
@RequestMapping("/chat")
@Api(value = "在线聊天",tags = "在线聊天")
public class ChatController {
    @Autowired
    private IAdminService iAdminService;

    @ApiOperation(value = "获取所有操作员")
    @GetMapping("/admin")
    public List<Admin> getAllAdmins(String keywords){
        //可以搜索和谁聊天
        return iAdminService.getAllAdmins(keywords);
    }


}