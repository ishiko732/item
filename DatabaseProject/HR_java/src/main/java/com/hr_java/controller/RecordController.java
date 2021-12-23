package com.hr_java.controller;

import com.hr_java.Model.VO.Result;
import com.hr_java.Model.entity.User;
import com.hr_java.Model.entity.Transfer;
import com.hr_java.service.DepartmentService;
import com.hr_java.service.TransferService;
import com.hr_java.service.UserService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
    @RequiresPermissions(logical = Logical.AND, value = {"档案登记"}) //需要包含权限值那些
    public Result register(User user){
        Boolean register = userService.register(user);
        if(register){
            return Result.succ(user);
        }
        return Result.fail("注册失败");
    }

    @PostMapping(value="/updateUID")
    @RequiresPermissions(logical = Logical.AND, value = {"档案变更"}) //需要包含权限值那些
    public Result updateUID(User user){
        Boolean register = userService.updateByUID(user);
        if(register){
            return Result.succ(user);
        }
        return Result.fail("更新失败");
    }

    /** 设置员工薪酬标准，档案调动
     通过修改职称来设置，因此通过调动模块来实现
     **/
    @PostMapping("/transferPosition")
    @RequiresPermissions(logical = Logical.AND, value = {"档案删除","档案复核","档案恢复"}) //需要包含权限值那些
    public Result transferPosition(Long uid,Integer rid,Integer pid) {
        //需要在调动管理模块中进行机构和职位修改
        //可不能修改档案编号
        User user = userService.getById(uid);
        user.setRid(rid);
        user.setPid(pid);
        Boolean transfer = userService.updateByUID_transfer(user);
        Result ret;
        if(transfer){
            ret=Result.succ("已申请调动！");
        }else{
            ret=Result.fail("调动失败！");
        }
        return ret;
    }


}
