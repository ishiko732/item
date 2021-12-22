package com.hr_java.controller;

import com.hr_java.Model.VO.Result;
import com.hr_java.Model.entity.Salary;
import com.hr_java.security.JWTUtil;
import com.hr_java.security.UnauthorizedException;
import com.hr_java.service.SalaryService;
import com.hr_java.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private UserService userService;
    @Autowired
    private SalaryService salaryService;

    @GetMapping("/getSalary")
    @RequiresAuthentication //需要登录才能操作
    public Result getSalary(Long salaryId) {
        Result ret=null;
        if(Objects.isNull(salaryId)){
            Set<Salary> salaryList = salaryService.getSalaryList();
            ret=Result.succ(salaryList);
        }else{
            Salary salary =salaryService.getSalaryById(salaryId);
            ret=Result.succ(salary);
        }
        return ret;
    }

    @PostMapping("/Salary")
    @RequiresPermissions(logical = Logical.AND, value = {"薪酬标准登记"}) //需要包含权限值那些
    public Result insertSalary(@RequestBody Salary salary) {
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        String name = JWTUtil.getUsername(token);//操作员的姓名
        salary.setMRUName(name);
        boolean b = salaryService.insertSalary(salary);//同时插入subsidyName
        Result ret=null;
        if(b){
            ret=Result.succ("登记成功");
        }else{
            ret=Result.fail("登记失败");
        }
        return ret;
    }


}
