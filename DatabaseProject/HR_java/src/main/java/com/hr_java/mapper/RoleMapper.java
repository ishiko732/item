package com.hr_java.mapper;

import com.hr_java.Model.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 角色权限 Mapper 接口
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-17
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

}
