package com.hr_java.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr_java.Model.VO.UserJWT;
import com.hr_java.Model.entity.Department;
import com.hr_java.Model.entity.RecheckUser;
import com.hr_java.Model.entity.Transfer;
import com.hr_java.Model.entity.User;
import com.hr_java.mapper.UserMapper;
import com.hr_java.security.JWTUtil;
import com.hr_java.service.DepartmentService;
import com.hr_java.service.TransferService;
import com.hr_java.service.UserService;
import com.hr_java.utils.MD5;
import com.hr_java.security.UnauthorizedException;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    DepartmentService departmentService;
    @Autowired
    RecheckUserServiceImpl recheckUserService;
    @Autowired
    TransferService transferService;

    @Override
    public Boolean register(User user) {
        user.setUid(createUID(user));
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        Long uid = JWTUtil.getUID(token);
        LocalDateTime now = LocalDateTime.now();
        user.setBooker(uid);
        user.setRegistrantTime(now);
        System.out.println(user);
        user.setPassword(MD5.md5(user.getPassword()));//设置MD5密码
        int insertUser = getBaseMapper().insert(user);
        if (insertUser == 1) {
            RecheckUser recheckUser = new RecheckUser();
            recheckUser.setStatusID(0);
            recheckUser.setUid(user.getUid());
            recheckUser.setTid(null);
            insertUser = recheckUserService.getBaseMapper().insert(recheckUser);
        }
        return insertUser == 1;
    }

    @Override
    public Map<String, String> login(String username, String password) {
        System.err.println(username+":"+password);
        User user = getUserByName(username);
        if (Objects.isNull(user.getUid())) {
            throw new UnauthorizedException("账号不存在！");
        }
        if (user.getPassword().equals(MD5.md5(password))) {
            UserJWT userJWT = new UserJWT(user.getUid(), user.getName());
            String token = JWTUtil.sign(userJWT, user.getPassword());//临时token
            String refreshToken = JWTUtil.sign_refreshToken(user.getUid());//长期token
            HttpServletResponse response = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getResponse();
            if (!Objects.isNull(response)) {
                response.setHeader("Authorization", token);
                response.setHeader("refreshToken", refreshToken);//2天token
            }
            Map<String, String> ret = new HashMap<>();
            ret.put("Authorization", token);
            ret.put("refreshToken", refreshToken);
            return ret;
        } else {
            throw new UnauthorizedException("账号或密码不正确");
        }
    }

    @Override
    public void logout() {
//        HttpServletResponse response = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getResponse();
//        if (!Objects.isNull(response)) {
//            response.reset();//有效地删除了所有标头和任何缓冲数据
//        }
    }

    @Override
    public User getUserByName(String name) {
        return getBaseMapper().getUserByName(name);
    }

    @Override
    public User getById(Long id) {
        return getBaseMapper().getById(id);
    }

    @Override
    public Boolean updateByUID(User user) {
        //对人资档案数据进行更新，包括薪酬标准的调整。
        //所属机构和职位不能修改，需要在调动管理模块中进行
        //可修改除档案编号、所属机构和职位外的员工信息。
        user.setRid(null);
        user.setPid(null);//所在部门在职称那里
        return getBaseMapper().updateById(user) == 1;
    }

    @Override
    public Boolean updateByUID_transfer(User user) {//这里允许薪酬标准更新 通过修改职位里的薪酬标准来实现
        //调动职位，角色，薪酬等信息
        Transfer transfer = new Transfer(null,user.getUid(),user.getRid(),user.getPid());
        transferService.getBaseMapper().insert(transfer);
        boolean updateUser = updateByUID(user); //通过这个修改不需要审核的部分
        if (updateUser ) {
            RecheckUser recheckUser = new RecheckUser();
            recheckUser.setRUserId(recheckUserService.getRUidByUid(user.getUid()));
            recheckUser.setStatusID(0);
            recheckUser.setUid(user.getUid());
            recheckUser.setTid(transfer.getTid());//调用角色，职位信息
            recheckUser.setRecheckTime(LocalDateTime.now());
            String token = (String) SecurityUtils.getSubject().getPrincipal();
            String name = JWTUtil.getUsername(token);//操作员的姓名
            recheckUser.setCheckUserName(name);
            updateUser = recheckUserService.getBaseMapper().updateById(recheckUser)==1;
        }
        return updateUser;
    }

    public Long createUID(User user) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        Integer fid3 = user.getFid();
        Integer fid1 = 0, fid2 = 0;
        if (!Objects.isNull(fid3)) {
            Department department3 = departmentService.getBaseMapper().selectById(fid3);
            Department department2 = departmentService.getBaseMapper().selectById(department3.getParentId());
            fid1 = department2.getParentId();
            fid2 = department2.getDeptID();
            fid3 = department3.getDeptID();
        }
        String uid = String.valueOf(year);
        if (fid1 < 9) {
            uid += "0" + fid1;
        }
        if (fid2 < 9) {
            uid += "0" + fid2;
        }
        if (fid3 < 9) {
            uid += "0" + fid3;
        }
        Long count = getBaseMapper().userEndID(uid + "0000", uid + "9999") + 1;

        return Long.valueOf(uid + String.format("%04d", count));


    }

    @Override
    public List<User> getUserList() {
        return getBaseMapper().getUserList();
    }

    @Override
    public List<User> selectUser(String fid, String pcId, String pid,LocalDateTime time1, LocalDateTime time2) {
        List<User> userList = getUserList();
        Set<Long> uids = getBaseMapper().selectUid(fid, pcId, pid, time1, time2);
        userList.removeIf(user -> !uids.contains(user.getUid()));
        return userList;
    }
}
