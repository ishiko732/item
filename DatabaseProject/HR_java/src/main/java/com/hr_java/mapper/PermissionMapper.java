package com.hr_java.mapper;

import com.hr_java.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-17
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

}
