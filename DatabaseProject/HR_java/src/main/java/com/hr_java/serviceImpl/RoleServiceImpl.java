package com.hr_java.serviceImpl;

import com.hr_java.Model.entity.Role;
import com.hr_java.mapper.RoleMapper;
import com.hr_java.security.JWTUtil;
import com.hr_java.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色权限 服务实现类
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-17
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Override
    public Role getById(Integer id) {
        return getBaseMapper().getById(id);
    }

    @Override
    public Role getByUid(Long uid) {
        return getBaseMapper().getByUid(uid);
    }
}
