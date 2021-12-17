package com.hr_java.controller;

import com.hr_java.config.Result;
import com.hr_java.entity.RecheckUser;
import com.hr_java.entity.User;
import com.hr_java.service.DepartmentService;
import com.hr_java.service.RecheckUserService;
import com.hr_java.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

//人力资源管理 前端控制器
@RestController
@RequestMapping("/record")
public class recordController {
    @Autowired
    DepartmentService departmentService;
    @Autowired
    UserService userService;
    @Autowired
    RecheckUserService recheckUserService;

    @GetMapping(value = "/department")//获取部门列表
    public Result getDepartment() {
        return Result.succ(departmentService.selectByDepAll());
    }

    @GetMapping(value = "/reDepartment/{id}")//获取部门列表-反向
    public Result getReDepartment(@PathVariable Integer id) {
        return Result.succ(departmentService.reSelectByDep(id));
    }

    @PostMapping(value="/register")
    public Result register(User user){
        Boolean register = userService.register(user);
        if(register){
            return Result.succ(user);
        }
        return Result.fail("注册失败");
    }

    @PostMapping(value="/updateUID")
    public Result updateUID(User user){
        Boolean register = userService.updateByUID(user);
        if(register){
            return Result.succ(user);
        }
        return Result.fail("更新失败");
    }

    @GetMapping(value = "/checkUser")
    public Result checkUserALL(Integer id){
        List<RecheckUser> recheckUsers = recheckUserService.list();
        if(!Objects.isNull(id)){ //非空，排除其他状态
            for (int i = recheckUsers.size()-1; i>=0; i--) {
               if(!Objects.equals(recheckUsers.get(i).getStatusID(), id)){
                   recheckUsers.remove(i);
               }
            }
        }
        for (RecheckUser recheckUser : recheckUsers) {
            User user = userService.getById(recheckUser.getUid());
            user.setPassword(null);
            recheckUser.setUser(user);
        }
        return Result.succ(recheckUsers);
    }

    @GetMapping(value = "/checkUser/{id}")
    public Result checkUser(Integer id){
        List<RecheckUser> recheckUsers = recheckUserService.list();
        if(!Objects.isNull(id)){ //非空，排除其他状态
            for (int i = recheckUsers.size()-1; i>=0; i--) {
                if(!Objects.equals(recheckUsers.get(i).getStatusID(), id)){
                    recheckUsers.remove(i);
                }
            }
        }
        for (RecheckUser recheckUser : recheckUsers) {
            User user = userService.getById(recheckUser.getUid());
            user.setPassword(null);
            recheckUser.setUser(user);
        }
        return Result.succ(recheckUsers);
    }


}
