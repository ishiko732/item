package com.hr_java.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hr_java.Model.entity.RecheckUser;
import com.hr_java.Model.entity.Transfer;
import com.hr_java.Model.entity.User;
import com.hr_java.mapper.RecheckUserMapper;
import com.hr_java.security.JWTUtil;
import com.hr_java.service.RecheckUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr_java.service.TransferService;
import com.hr_java.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>
 * 复核用户 服务实现类
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-12
 */
@Service
public class RecheckUserServiceImpl extends ServiceImpl<RecheckUserMapper, RecheckUser> implements RecheckUserService {

    @Autowired
    TransferService transferService;
    @Override
    public boolean updateByRID(RecheckUser recheckUser) {
        //复核过程中，档案编号、该员工所属机构和职位不能修改，其他信息均可修改。复核通过后该员工档案生效。
        RecheckUser recheckUserBySql = getBaseMapper().getById(recheckUser.getRUserId());
        Integer tid = recheckUserBySql.getTid();
        if(!Objects.isNull(tid)){//调动情况不为空
            transferService.transferUserByUID(tid);//通过tid调动查询到用户，并修改rid，pid
            recheckUser.setTid(null);
        }
//        System.err.println(recheckUserBySql);
        recheckUser.setUid(null);//防止uid被修改
        recheckUser.setRecheckTime(LocalDateTime.now());
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        String name = JWTUtil.getUsername(token);//操作员的姓名
        recheckUser.setCheckUserName(name);
        boolean ret = this.getBaseMapper().updateById(recheckUser) == 1;
        recheckUser.setUid(recheckUserBySql.getUid());
        recheckUser.setTid(tid);
        return ret;
    }

    @Override
    public boolean deleteByUID(Long uid) {
        return getBaseMapper().deleteUserByUid(uid);
    }

    @Override
    public Integer getRUidByUid(Long uid) {
        return getBaseMapper().getRUidByUid(uid);
    }
}
