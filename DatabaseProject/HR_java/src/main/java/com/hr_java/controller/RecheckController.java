package com.hr_java.controller;


import com.hr_java.Model.VO.Result;
import com.hr_java.Model.entity.ReCheckSalary;
import com.hr_java.Model.entity.RecheckUser;
import com.hr_java.Model.entity.User;
import com.hr_java.service.ReCheckSalaryService;
import com.hr_java.service.RecheckUserService;
import com.hr_java.service.UserService;
import org.apache.ibatis.annotations.Delete;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        recheckUserService.updateByRID(recheckUser);
        if(!Objects.isNull(user.getUid())){
            userService.updateByUID(user);
            //TODO 这里允许薪酬标准更新
        }
        return Result.succ(recheckUser);
    }

    @DeleteMapping(value = "/checkUser/{uid}")
    @RequiresPermissions(logical = Logical.AND, value = {"档案复核","档案查询","档案变更","档案删除"}) //需要包含权限值那些
    public Result deleteUserById(@PathVariable Long uid){//更新资料
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


}
