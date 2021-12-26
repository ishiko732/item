package com.hr_java.controller;


import com.hr_java.Model.VO.Result;
import com.hr_java.Model.entity.*;
import com.hr_java.security.JWTUtil;
import com.hr_java.service.*;
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
import java.util.Set;

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
    @Autowired
    TransferService transferService;
    @Autowired
    RoleService roleService;
    @Autowired
    RecheckSerialService recheckSerialService;
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
            User user=isTransferUser(recheckUser);
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
        User user=isTransferUser(recheckUser);
        recheckUser.setUser(user);
        return Result.succ(recheckUser);
    }

    private User isTransferUser(RecheckUser recheckUser) {
        User user = userService.getById(recheckUser.getUid());
        if(!Objects.isNull(recheckUser.getTid())){
            Transfer transfer = transferService.getById(recheckUser.getTid());
            user.setRid(transfer.getRid());
            user.setPid(transfer.getPid());
            user.setRole(roleService.getById(transfer.getRid()));
        }
        return user;
    }

    @PutMapping(value = "/checkUser/{rid}")
    @RequiresPermissions(logical = Logical.AND, value = {"档案复核","档案查询","档案变更"}) //需要包含权限值那些
    public Result checkUserById(@PathVariable("rid")Integer id,RecheckUser recheckUser,User user){//更新资料
        recheckUser.setRUserId(id);//防止修改RID
        recheckUserService.updateByRID(recheckUser);
        if(!Objects.isNull(user.getUid())){
            userService.updateByUID(user);
        }
        return Result.succ(recheckUser);
    }

    @DeleteMapping(value = "/checkUser/{uid}")
    @RequiresPermissions(logical = Logical.AND, value = {"档案复核","档案查询","档案变更","档案删除"}) //需要包含权限值那些
    public Result deleteUserById(@PathVariable Long uid){//删除资料
        boolean b = recheckUserService.deleteByUID(uid);
        Result succ;
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
    @PutMapping(value = "/checkSalary/{rid}")
    @RequiresPermissions(logical = Logical.AND, value = {"薪酬标准复核","薪酬标准查询","薪酬标准变更"}) //需要包含权限值那些
    public Result checkSalaryById(@PathVariable("rid")Integer id,ReCheckSalary reCheckSalary,Salary salary){//更新资料
        Result succ;
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        String name = JWTUtil.getUsername(token);//操作员的姓名
        reCheckSalary.setCheckUserName(name);
        reCheckSalary.setRSalaryId(id);
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
        Result succ;
        if(b){
            succ = Result.succ("删除成功！");
        }else{
            succ = Result.fail("删除失败！");
        }
        return succ;
    }

    //复核薪酬发放，只需要审核通过就对接财务处发放工资
    //复核薪酬发放
    @GetMapping(value = "/checkSerial")
    @RequiresPermissions(logical = Logical.AND, value = {"薪酬标准查询"}) //需要包含权限值那些
    public Result checkSerialByYear_Month(Integer year,Integer month){
        Set<RecheckSerial> recheckSerials = recheckSerialService.getList();
        return Result.succ(recheckSerials);
    }

    @GetMapping(value = "/checkSerial/{id}")
    @RequiresPermissions(logical = Logical.AND, value = {"薪酬标准复核","薪酬标准查询"}) //需要包含权限值那些
    public Result getSerialById(@PathVariable Integer id){//获取薪酬标准某个资料
        RecheckSerial recheckSerialerial = recheckSerialService.getById(id);
        if(Objects.isNull(recheckSerialerial)){
            return Result.fail("不存在！");
        }
        return Result.succ(recheckSerialerial);
    }
    @PutMapping(value = "/checkSerial/{rid}")
    @RequiresPermissions(logical = Logical.AND, value = {"薪酬标准复核","薪酬标准查询","薪酬标准变更","发放复核",}) //需要包含权限值那些
    public Result checkSerialById(@PathVariable("rid")Integer id,RecheckSerial recheckSerial){//更新资料
        Result succ;
        recheckSerial.setRSerialId(id);
        if(Objects.isNull(recheckSerial.getMessage())){
            recheckSerial.setMessage("无意见");
        }
        System.out.println(recheckSerial);
        boolean b = recheckSerialService.updateById(recheckSerial);
        if(b){
            succ = Result.succ("复核成功！");
        }else{
            succ = Result.fail("复核失败！");
        }
        return succ;
    }

    @DeleteMapping(value = "/checkSerial/{rid}")
    @RequiresPermissions(logical = Logical.AND, value = {"薪酬标准复核","薪酬标准查询","薪酬标准变更","发放复核"}) //需要包含权限值那些
    public Result deleteSerialById(@PathVariable("rid") Integer id,String message){//更新资料
        if(Objects.isNull(message)){
            message="已删除该条目";
        }
        boolean b = recheckSerialService.deleteById(id,message);
        Result succ;
        if(b){
            succ = Result.succ("删除成功！");
        }else{
            succ = Result.fail("删除失败！");
        }
        return succ;
    }

}
