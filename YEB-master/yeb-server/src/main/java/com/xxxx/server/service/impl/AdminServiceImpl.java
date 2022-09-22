package com.xxxx.server.service.impl;

import ch.qos.logback.core.joran.action.IADataForComplexProperty;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.server.utils.AdminUtils;
import com.xxxx.server.config.security.component.JwtTokenUtils;
import com.xxxx.server.mapper.AdminMapper;
import com.xxxx.server.mapper.AdminRoleMapper;
import com.xxxx.server.mapper.RoleMapper;
import com.xxxx.server.pojo.Admin;
import com.xxxx.server.pojo.AdminRole;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.pojo.Role;
import com.xxxx.server.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author LHC
 * @since 2021-06-25
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    private AdminMapper adminMapper;//配置了扫描包，实际上已经注入了 但是还是爆红，不管

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private IAdminService adminService;

    //密码加密
    @Autowired
    private PasswordEncoder passwordEncoder;

    //密码加密
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    //将配置文件中存的值取过来
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private AdminRoleMapper adminRoleMapper;

    /**
     * 登录之后返回token
     *
     * @param username
     * @param password
     * @param code
     * @param request
     * @return
     */
    @Override
    public RespBean login(String username, String password, String code, HttpServletRequest request) {
        //获取验证码
        String defaultKaptcha = (String) request.getSession().getAttribute("captcha");
        System.out.println("登录验证的验证码：" + code);
        if (StringUtils.isEmpty(code) || !defaultKaptcha.equalsIgnoreCase(code)) {
            return RespBean.error("验证码不正确，请重新输入！");
        }

        System.out.println(username);
        //登录，admin 对象中没有权限字段
        Admin admin = adminService.getAdmin(username);
        if (admin == null || !bCryptPasswordEncoder.matches(password, admin.getPassword())) {
            return RespBean.error("用户名或密码不正确，请重新输入！");
        }

        //判断账号是否被禁用
        if (admin.getIntEnabled() == 0) {
            return RespBean.error("账号被禁用,请联系管理员");
        }
        //这个获取的管理员用户有权限字段
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        // 更新security登录用户对象
        // null 的位置本来该放密码，但是一般是不放密码的
        // userDetails.getAuthorities() 是权限列表
        System.out.println(userDetails.getAuthorities());
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());
        // 放在security全局里面
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 到这里说明没问题 就让他拿到令牌
        String token = jwtTokenUtils.generateToken(userDetails);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);//返回头部信息

        // 登录成功之后返回一个token给前端
        return RespBean.success("登录成功", tokenMap);//tokenMap中有 username，new Date（），tokenHead
    }

    /**
     * 根据用户名获取用户
     *
     * @param username
     * @return
     */
    @Override
    public Admin getAdminByUserName(String username) {
        //使用MybatisPlus的单个查询方法，selectOne 用.eq 比较方法在匹配查询条件
        return adminMapper.selectOne(new QueryWrapper<Admin>().eq("username", username).eq("enabled", true));
    }

    /**
     * 根据用户id查询角色列表
     *
     * @param adminId
     * @return
     */
    @Override
    public List<Role> getRoles(Integer adminId) {

        return roleMapper.getRoles(adminId);
    }

    /**
     * 获取所有操作员
     *
     * @param keywords
     * @return
     */
    @Override
    public List<Admin> getAllAdmins(String keywords) {

        return adminMapper.getAllAdmins(AdminUtils.getCurrentAdmin().getId(), keywords);
    }

    /**
     * 更新操作员角色
     *
     * @param adminId
     * @param rids
     * @return
     */
    @Override
    @Transactional
    public RespBean updateAdminRole(Integer adminId, List<Integer> rids) {
        adminRoleMapper.delete(new QueryWrapper<AdminRole>().eq("adminId", adminId));

        Integer result = adminRoleMapper.addAdminRole(adminId, rids);
        if (result == rids.size()) return RespBean.success("更新成功！");
        return RespBean.error("更新失败！");
    }

    /**
     * 个人中心 修改用户密码
     *
     * @param oldPass
     * @param pass
     * @param adminId
     * @return
     */
    @Override
    public RespBean updateAdminPassWord(String oldPass, String pass, Integer adminId) {
        Admin admin = adminMapper.selectById(adminId);
        // 密码是加密过的
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // 比较旧密码是否和未更新的数据库密码是否一样，不一样则不让修改
        if (encoder.matches(oldPass, admin.getPassword())) {
            // 设置新密码，加密
            admin.setPassword(encoder.encode(pass));
            int result = adminMapper.updateById(admin);
            System.out.println(result);
            if (result == 1) {
                return RespBean.success("更新成功!");
            }
        }
        return RespBean.error("更新失败！");
    }

    /**
     * 更新用户头像
     *
     * @param url
     * @param id
     * @param authentication
     * @return
     */
    @Override
    public RespBean updateAdminUserFace(String url, Integer id, Authentication authentication) {
        Admin admin = adminMapper.selectById(id);
        admin.setUserFace(url);
        int result = adminMapper.updateById(admin);
        if (result == 1) {
            //更新当前用户的全局信息
            Admin principal = (Admin) authentication.getPrincipal();
            principal.setUserFace(url);
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(admin, null, authentication.getAuthorities()));
            return RespBean.success("更新成功！", url);
        }
        return RespBean.error("更新失败！");
    }

    /**
     * 获取管理员
     * @param username
     * @return
     */
    @Override
    public Admin getAdmin(String username) {
        Admin admin = adminMapper.getAdmin(username);
        return admin;
    }

}
