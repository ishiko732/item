package com.hr_java.controller;


import com.hr_java.config.Result;
import com.hr_java.entity.RecheckUser;
import com.hr_java.entity.User;
import com.hr_java.service.RecheckUserService;
import com.hr_java.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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

}
