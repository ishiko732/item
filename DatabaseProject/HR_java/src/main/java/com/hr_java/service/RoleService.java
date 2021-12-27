package com.hr_java.service;

import com.hr_java.Model.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 角色权限 服务类
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-17
 */
public interface RoleService extends IService<Role> {
    Role getById(Integer id);
    Role getByUid(Long uid);
}
