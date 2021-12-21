package com.hr_java.controller;

import com.hr_java.Model.entity.User;
import com.hr_java.Model.VO.UserJWT;
import com.hr_java.security.JWTUtil;
import com.hr_java.service.UserService;
import com.hr_java.utils.HttpCodeEnum;
import com.hr_java.utils.MD5;
import com.hr_java.Model.VO.Result;
import com.hr_java.utils.UnauthorizedException;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserService userService;

    @Autowired
    public void setService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public Result login(@RequestParam("name") String username,
                              @RequestParam("password") String password) {
        User user = userService.getUserByName(username);
        if(Objects.isNull(user.getUid())){
            throw new UnauthorizedException();
        }
        if (user.getPassword().equals(MD5.md5(password))) {
            UserJWT userJWT = new UserJWT(user.getUid(), user.getName());
            String token = JWTUtil.sign(userJWT, user.getPassword());
            HttpServletResponse response = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getResponse();
            if(!Objects.isNull(response)){
                response.setHeader("Authorization", token);
            }
            return Result.succ(token);
        } else {
            throw new UnauthorizedException();
        }
    }

    @GetMapping("/article")
    public Result article() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return Result.succ(HttpCodeEnum.OK, "You are already logged in");
        } else {
            return Result.succ(HttpCodeEnum.OK, "You are guest");
        }
    }

    @GetMapping("/require_auth")
    @RequiresAuthentication //需要登录才能操作
    public Result requireAuth() {
        return Result.succ(HttpCodeEnum.OK, "You are authenticated");
    }

    @GetMapping("/require_role")
    @RequiresRoles("超级管理员") //需要角色为superadmin
    public Result requireRole() {
        return Result.succ(HttpCodeEnum.OK, "You are visiting require_role");
    }

    @GetMapping("/require_permission")
    @RequiresPermissions(logical = Logical.AND, value = {"232",}) //需要包含权限值那些
    public Result requirePermission() {
        return Result.succ(HttpCodeEnum.OK, "You are visiting permission require edit,view");
    }

    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result unauthorized() {
        return Result.fail(HttpCodeEnum.UNAUTHORIZED,null);
    }

}
