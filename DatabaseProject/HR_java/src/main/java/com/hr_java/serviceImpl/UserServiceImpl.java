package com.hr_java.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr_java.Model.VO.UserJWT;
import com.hr_java.Model.entity.Department;
import com.hr_java.Model.entity.RecheckUser;
import com.hr_java.Model.entity.User;
import com.hr_java.mapper.UserMapper;
import com.hr_java.security.JWTUtil;
import com.hr_java.service.DepartmentService;
import com.hr_java.service.UserService;
import com.hr_java.utils.MD5;
import com.hr_java.utils.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Objects;

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

    @Override
    public Boolean register(User user) {
        user.setUid(createUID(user));
        System.out.println(user);
        user.setPassword(MD5.md5(user.getPassword()));//设置MD5密码
        int insertUser = getBaseMapper().insert(user);
        if (insertUser == 1) {
            RecheckUser recheckUser = new RecheckUser();
            recheckUser.setStatusID(0);
            recheckUser.setUid(user.getUid());
            insertUser=recheckUserService.getBaseMapper().insert(recheckUser);
        }
        return insertUser==1;
    }

    @Override
    public String login(String username, String password) {
        User user = getUserByName(username);
        if(Objects.isNull(user.getUid())){
            throw new UnauthorizedException();
        }
        if (user.getPassword().equals(MD5.md5(password))) {
            UserJWT userJWT = new UserJWT(user.getUid(), user.getName());
            String token = JWTUtil.sign(userJWT, user.getPassword());
            HttpServletResponse response = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getResponse();
            if(!Objects.isNull(response)){
                response.setHeader("Authorization", token);
            }
            return token;
        } else {
            throw new UnauthorizedException();
        }
    }

    @Override
    public void logout() {
        HttpServletResponse response = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getResponse();
        if(!Objects.isNull(response)){
            response.reset();//有效地删除了所有标头和任何缓冲数据
        }
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
        //对人资档案数据进行更新，包括薪酬标准的调整。所属机构和职位不能修改，需要在调动管理模块中进行
        //可修改除档案编号、所属机构和职位外的员工信息。
        user.setFid(null);
        user.setRid(null);
        user.setPid(null);

        return  getBaseMapper().updateById(user)==1;
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
        Long count=getBaseMapper().userEndID(uid+"0000",uid+"9999")+1;

        return Long.valueOf(uid+String.format("%04d",count));


    }

}
