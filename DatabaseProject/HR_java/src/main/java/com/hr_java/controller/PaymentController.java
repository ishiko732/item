package com.hr_java.controller;

import com.hr_java.Model.VO.Result;
import com.hr_java.Model.entity.Salary;
import com.hr_java.security.JWTUtil;
import com.hr_java.service.SalaryService;
import com.hr_java.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        Result ret = null;
        if (Objects.isNull(salaryId)) {
            Set<Salary> salaryList = salaryService.getSalaryList();
            ret = Result.succ(salaryList);
        } else {
            Salary salary = salaryService.getSalaryById(salaryId);
            ret = Result.succ(salary);
        }
        return ret;
    }

    @PostMapping("/Salary")
    @RequiresPermissions(logical = Logical.AND, value = {"薪酬标准登记"}) //需要包含权限值那些
    public Result insertSalary(@RequestBody Salary salary) {
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        String name = JWTUtil.getUsername(token);//操作员的姓名
        salary.setRegisterName(name);
        System.err.println(salary);
        boolean b = salaryService.insertSalary(salary);//同时插入subsidyName
        Result ret = null;
        if (b) {
            ret = Result.succ("登记成功");
        } else {
            ret = Result.fail("登记失败");
        }
        return ret;
    }

    @GetMapping("/selectSalary")
    @RequiresPermissions(logical = Logical.AND, value = {"薪酬标准查询"}) //需要包含权限值那些
    public Result selectSalary(String salaryId, String salaryName, String MRUName, String registerName, String checkUserName, String time1, String time2) {
        LocalDateTime localDateTime1 = null, localDateTime2 = null;
        if (!Objects.isNull(time1)) {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            localDateTime1 = LocalDateTime.parse(time1, fmt);
            localDateTime2 = LocalDateTime.parse(time2, fmt);
            if (localDateTime1.compareTo(localDateTime2) < 0) {
                LocalDateTime temp = localDateTime1;
                localDateTime1 = localDateTime2;
                localDateTime2 = temp;
            }
        }
        Set<Salary> salaries = salaryService.selectSalaryList(salaryId, salaryName, MRUName, registerName, checkUserName, localDateTime1, localDateTime2);
        return Result.succ(salaries);
    }

}
