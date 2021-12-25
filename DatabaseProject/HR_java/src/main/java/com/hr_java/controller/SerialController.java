package com.hr_java.controller;


import com.hr_java.Model.VO.Result;
import com.hr_java.Model.entity.*;
import com.hr_java.service.DepartmentService;
import com.hr_java.service.SerialService;
import com.hr_java.service.UserService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * <p>
 * 发放薪水 前端控制器
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-12
 */
@RestController
@RequestMapping("/serial")
public class SerialController {
    @Autowired
    UserService userService;
    @Autowired
    SerialService serialService;
    //发放管理
    //依据部门生成薪酬发放编号
    @PostMapping("/generalSalary")
    @RequiresPermissions(logical = Logical.AND, value = {"发放登记"}) //需要包含权限值那些
    public Result generalSalary() {
        //通过当前的用户数来获取三级部门id
        List<User> userList = userService.getUserList();//已经注入部门id，注入角色，注入权限，状态（中文：正常，待审核，已删除）
        List<Serial> serials=new ArrayList<>();
        Set<RecheckSerial> recheckSerials=new HashSet<>();
        for (User user : userList) {
            if("正常".equals(user.getStatus())){
                Serial serial = new Serial();
                serial.setUid(user.getUid());
                serial.setUser(user);
                String payId = createPayId(user.getFid());
                serial.setPayrollID(payId);
                serials.add(serial);//修改为插入条目
                RecheckSerial recheckSerial=new RecheckSerial();
                recheckSerial.setPayrollID(payId);
                recheckSerials.add(recheckSerial);//插入复核条目
            }
            //TODO 通过判断注册日期是否大于30天，来判断是否允许加入;考核期间不加入，否则发放人数:0人
        }
        int ret = serialService.insertSerials(serials,recheckSerials);//插入全部包括审核信息
        return Result.succ(ret);
    }
    public String createPayId(Integer departmentId3) {
        Calendar cal = Calendar.getInstance();
        //SG- 3bit
        int year = cal.get(Calendar.YEAR);//4bit
        int month = cal.get(Calendar.MONTH)+1;//2bit
        //fid 2+2+2=6bit ==> 0000departmentId3
        //总计10bit
        return String.format("SG-%04d%02d%06d",year,month,departmentId3);//SG-202112000008
    }

}
