package com.hr_java.controller;


import com.hr_java.Model.VO.Result;
import com.hr_java.security.JWTUtil;
import com.hr_java.service.RoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 角色权限 前端控制器
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-17
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    final
    RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @RequestMapping("/{id}")
    @RequiresAuthentication //需要登录才能操作
    public Result get(@PathVariable("id") Integer id) {
        return Result.succ(roleService.getById(id));
    }

    @RequestMapping("/userinfo")
    @RequiresAuthentication //需要登录才能操作
    public Result getInfo(){
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        Long uid = JWTUtil.getUID(token);//操作员的uid
        return Result.succ(roleService.getByUid(uid));
    }
}
