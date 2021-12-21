package com.hr_java.security;


import com.hr_java.Model.VO.UserJWT;
import com.hr_java.Model.entity.Permission;
import com.hr_java.Model.entity.User;
import com.hr_java.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Service
public class MyRealm extends AuthorizingRealm {

    private static final Logger LOGGER = LogManager.getLogger(MyRealm.class);

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String name=JWTUtil.getUsername(principals.toString());
        User user = userService.getUserByName(name);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRole(user.getRole().getName());
        Set<String> permission = user.getRole().getPermissions();
        simpleAuthorizationInfo.addStringPermissions(permission);
        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String name = JWTUtil.getUsername(token);
        if(Objects.isNull(name)){
            throw new AuthenticationException("token invalid");
        }

        User user = userService.getUserByName(name);
        if (Objects.isNull(user)) {
            throw new AuthenticationException("User didn't existed!");
        }

        if (! JWTUtil.verify(token, new UserJWT(user.getUid(),user.getName()), user.getPassword())) {
            throw new AuthenticationException("Username or password error");
        }

        return new SimpleAuthenticationInfo(token, token, "my_realm");
    }
}