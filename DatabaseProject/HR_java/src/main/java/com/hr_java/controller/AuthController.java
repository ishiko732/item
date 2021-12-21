package com.hr_java.controller;

import com.hr_java.Model.entity.User;
import com.hr_java.Model.VO.UserJWT;
import com.hr_java.security.JWTUtil;
import com.hr_java.service.UserService;
import com.hr_java.utils.MD5;
import com.hr_java.Model.VO.Result;
import com.hr_java.utils.UnauthorizedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            return Result.succ(JWTUtil.sign(userJWT,user.getPassword()));
        } else {
            throw new UnauthorizedException();
        }

    }

}
