package com.hr_java.controller;

import com.hr_java.Model.VO.QueryRecordVO;
import com.hr_java.Model.VO.Result;
import com.hr_java.Model.entity.*;
import com.hr_java.service.*;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

//人力资源管理 前端控制器
@RestController
@RequestMapping("/record")
public class RecordController {
    @Autowired
    DepartmentService departmentService;
    @Autowired
    UserService userService;
    @Autowired
    JobTitlesService jobTitlesService;
    @Autowired
    PositionService positionService;
    @Autowired
    PositionClassificationService classificationService;


    @GetMapping(value = "/department")//获取部门列表
    public Result getDepartment() {
        List<Department> departments = departmentService.selectByDepAll();
        return Result.succ(departments);
    }

    @GetMapping(value = "/reDepartment/{id}")//获取部门列表-反向
    public Result getReDepartment(@PathVariable Integer id) {
        return Result.succ(departmentService.reSelectByDep(id));
    }

    @PostMapping(value = "/register")
    @RequiresPermissions(logical = Logical.AND, value = {"档案登记"}) //需要包含权限值那些
    public Result register(User user) {
        Boolean register = userService.register(user);
        if (register) {
            return Result.succ(user);
        }
        return Result.fail("注册失败");
    }

    @PostMapping(value = "/updateUID")
    @RequiresPermissions(logical = Logical.AND, value = {"档案变更"}) //需要包含权限值那些
    public Result updateUID(User user) {
        Boolean register = userService.updateByUID(user);
        if (register) {
            return Result.succ(user);
        }
        return Result.fail("更新失败");
    }

    /**
     * 设置员工薪酬标准，档案调动
     * 通过修改职称来设置，因此通过调动模块来实现
     **/
    @PostMapping("/transferPosition")
    @RequiresPermissions(logical = Logical.AND, value = {"档案删除", "档案复核", "档案恢复"}) //需要包含权限值那些
    public Result transferPosition(Long uid, Integer rid, Integer pid) {
        //需要在调动管理模块中进行机构和职位修改
        //可不能修改档案编号
        User user = userService.getById(uid);
        user.setRid(rid);
        user.setPid(pid);
        Boolean transfer = userService.updateByUID_transfer(user);
        Result ret;
        if (transfer) {
            ret = Result.succ("已申请调动！");
        } else {
            ret = Result.fail("调动失败！");
        }
        return ret;
    }

    @PostMapping("/selectRecord")
    @RequiresPermissions(logical = Logical.AND, value = {"档案查询"}) //需要包含权限值那些
    public Result selectRecord(QueryRecordVO queryRecordVO) {
        System.err.println(queryRecordVO);
        LocalDateTime localDateTime1 = null, localDateTime2 = null;
        if (!Objects.isNull(queryRecordVO.getTime1())) {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            localDateTime1 = LocalDateTime.parse(queryRecordVO.getTime1(), fmt);
            localDateTime2 = LocalDateTime.parse(queryRecordVO.getTime2(), fmt);
            if (localDateTime1.compareTo(localDateTime2) < 0) {
                LocalDateTime temp = localDateTime1;
                localDateTime1 = localDateTime2;
                localDateTime2 = temp;
            }
        }
        List<User> users = userService.selectUser(queryRecordVO.getFid(), queryRecordVO.getPcId(), queryRecordVO.getPid(), localDateTime1, localDateTime2);
        for (User user : users) {
            Position position = positionService.getById(user.getPid());
            user.setJobTitles(position.getName());
            Department department = departmentService.reSelectByDep(user.getFid());
            user.setDepartment(department);
        }
        return Result.succ(users);
    }

    @GetMapping("/jobTitles")
    public Result selectJobTitles() {
        List<JobTitles> list = jobTitlesService.list();
        return Result.succ(list);
    }

    @GetMapping("/position")
    public Result selectPosition() {
        List<Position> list = positionService.list();
        return Result.succ(list);
    }

    @GetMapping("/position/{id}")
    public Result getPositionByClassificationId(@PathVariable("id")Integer pcid) {
        List<Position> list = classificationService.getByPCid(pcid);
        return Result.succ(list);
    }

    @GetMapping("/classification")
    public Result selectClassification() {
        List<PositionClassification> list = classificationService.list();
        return Result.succ(list);
    }
}
