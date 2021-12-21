package com.hr_java.serviceImpl;

import com.hr_java.Model.entity.RecheckUser;
import com.hr_java.mapper.RecheckUserMapper;
import com.hr_java.service.RecheckUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
    @Override
    public boolean updateByRID(RecheckUser recheckUser) {
        //复核过程中，档案编号、该员工所属机构和职位不能修改，其他信息均可修改。复核通过后该员工档案生效。
        RecheckUser recheckUser1 = getById(recheckUser.getRUserId());
        recheckUser.setUid(null);
        LocalDateTime now = LocalDateTime.now();
        recheckUser.setRecheckTime(now);
        //TODO 添加登录权限以后设置
//        recheckUser.setCheckUserName();
        boolean ret = getBaseMapper().updateById(recheckUser) == 1;
        recheckUser.setUid(recheckUser1.getUid());
        return ret;
    }
}
