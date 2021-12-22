package com.hr_java.controller;


import com.hr_java.Model.VO.Result;
import com.hr_java.Model.entity.ReCheckSalary;
import com.hr_java.Model.entity.RecheckUser;
import com.hr_java.Model.entity.Salary;
import com.hr_java.Model.entity.User;
import com.hr_java.security.JWTUtil;
import com.hr_java.service.ReCheckSalaryService;
import com.hr_java.service.RecheckUserService;
import com.hr_java.service.SalaryService;
import com.hr_java.service.UserService;
import org.apache.ibatis.annotations.Delete;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 复核用户，复核薪水标准，复核发放 前端控制器
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-12
 */
@RestController
@RequestMapping("/recheck")
public class RecheckController {
    @Autowired
    UserService userService;
    @Autowired
    SalaryService salaryService;
    @Autowired
    RecheckUserService recheckUserService;
    @Autowired
    ReCheckSalaryService reCheckSalaryService;

    //复核用户
    @GetMapping(value = "/checkUser")
    @RequiresPermissions(logical = Logical.AND, value = {"档案复核"}) //需要包含权限值那些
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
    @RequiresPermissions(logical = Logical.AND, value = {"档案复核","档案查询"}) //需要包含权限值那些
    public Result getUserById(@PathVariable Integer id){//获取资料
        RecheckUser recheckUser = recheckUserService.getById(id);
        if(Objects.isNull(recheckUser)){
            return Result.fail("不存在！");
        }
        User user = userService.getById(recheckUser.getUid());
        recheckUser.setUser(user);
        return Result.succ(recheckUser);
    }

    @PutMapping(value = "/checkUser/{id}")
    @RequiresPermissions(logical = Logical.AND, value = {"档案复核","档案查询","档案变更"}) //需要包含权限值那些
    public Result checkUserById(RecheckUser recheckUser,User user){//更新资料
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        String name = JWTUtil.getUsername(token);//操作员的姓名
        recheckUser.setRecheckTime(LocalDateTime.now());
        recheckUser.setCheckUserName(name);
        recheckUserService.updateByRID(recheckUser);
        if(!Objects.isNull(user.getUid())){
            userService.updateByUID(user);
            //TODO 这里允许薪酬标准更新
        }
        return Result.succ(recheckUser);
    }

    @DeleteMapping(value = "/checkUser/{uid}")
    @RequiresPermissions(logical = Logical.AND, value = {"档案复核","档案查询","档案变更","档案删除"}) //需要包含权限值那些
    public Result deleteUserById(@PathVariable Long uid){//删除资料
        boolean b = recheckUserService.deleteByUID(uid);
        Result succ=null;
        if(b){
            succ = Result.succ("删除成功！");
        }else{
            succ = Result.fail("删除失败！");
        }
        return succ;
    }


    //复核薪酬标准
    @GetMapping(value = "/checkSalary")
    @RequiresPermissions(logical = Logical.AND, value = {"薪酬标准复核"}) //需要包含权限值那些
    public Result checkSalaryALL(Integer id){
        List<ReCheckSalary> reCheckSalaries=reCheckSalaryService.list();
        if(!Objects.isNull(id)){ //非空，排除其他状态
            for (int i = reCheckSalaries.size()-1; i>=0; i--) {
                if(!Objects.equals(reCheckSalaries.get(i).getStatusID(), id)){//statusID一致
                    reCheckSalaries.remove(i);
                }
            }
        }
        for (ReCheckSalary reCheckSalary : reCheckSalaries) {
            Salary salary = salaryService.getSalaryById(reCheckSalary.getSalaryId());
            reCheckSalary.setSalary(salary);
        }
        return Result.succ(reCheckSalaries);
    }

    @GetMapping(value = "/checkSalary/{id}")
    @RequiresPermissions(logical = Logical.AND, value = {"薪酬标准复核","薪酬标准查询"}) //需要包含权限值那些
    public Result getSalaryById(@PathVariable Integer id){//获取薪酬标准某个资料
        ReCheckSalary reCheckSalary = reCheckSalaryService.getById(id);
        if(Objects.isNull(reCheckSalary)){
            return Result.fail("不存在！");
        }
        Salary salary = salaryService.getSalaryById(reCheckSalary.getSalaryId());
        reCheckSalary.setSalary(salary);
        return Result.succ(reCheckSalary);
    }
    @PutMapping(value = "/checkSalary/{id}")
    @RequiresPermissions(logical = Logical.AND, value = {"薪酬标准复核","薪酬标准查询","薪酬标准变更"}) //需要包含权限值那些
    public Result checkSalaryById(ReCheckSalary reCheckSalary,Salary salary){//更新资料
        Result succ;
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        String name = JWTUtil.getUsername(token);//操作员的姓名
        reCheckSalary.setCheckUserName(name);
        if(Objects.isNull(reCheckSalary.getMessage())){
            reCheckSalary.setMessage("无意见");
        }
        System.out.println(reCheckSalary);
        boolean b = reCheckSalaryService.updateById(reCheckSalary);
        if(!Objects.isNull(salary.getSalaryId())){
            salaryService.updateById(salary);
        }
        if(b){
            succ = Result.succ("复核成功！");
        }else{
            succ = Result.fail("复核失败！");
        }
        return succ;
    }

    @DeleteMapping(value = "/checkSalary/{rSalaryId}")
    @RequiresPermissions(logical = Logical.AND, value = {"薪酬标准复核","薪酬标准查询","薪酬标准变更","发放复核"}) //需要包含权限值那些
    public Result deleteSalaryById(@PathVariable Long rSalaryId){//更新资料
        boolean b = reCheckSalaryService.deleteBySalaryId(rSalaryId);
        Result succ=null;
        if(b){
            succ = Result.succ("删除成功！");
        }else{
            succ = Result.fail("删除失败！");
        }
        return succ;
    }

}
