package com.hr_java.controller;

import com.hr_java.Model.VO.Result;
import com.hr_java.Model.entity.User;
import com.hr_java.service.DepartmentService;
import com.hr_java.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//人力资源管理 前端控制器
@RestController
@RequestMapping("/record")
public class RecordController {
    @Autowired
    DepartmentService departmentService;
    @Autowired
    UserService userService;


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




}
