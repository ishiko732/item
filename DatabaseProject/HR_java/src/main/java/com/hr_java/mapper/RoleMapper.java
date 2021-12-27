package com.hr_java.mapper;

import com.hr_java.Model.entity.Permission;
import com.hr_java.Model.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Set;

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
    @Select("select * from role where rid=#{id}")
    @Results({
            @Result(property = "rid",column = "rid"),
            @Result(property = "permissions",javaType = Set.class,column="rid",
                    many=@Many(select = "com.hr_java.mapper.RoleMapper.getPermissionByRid"))
    })
    Role getById(@Param("id")Integer id);

    @Select("select permission from permission_role where rid=#{id}")
    Set<String> getPermissionByRid(@Param("id")Integer id);

    @Select("select * from role where rid=(select rid\n" +
            "                              from user where uid=#{uid})")
    @Results({
            @Result(property = "rid",column = "rid"),
            @Result(property = "permissions",javaType = Set.class,column="rid",
                    many=@Many(select = "com.hr_java.mapper.RoleMapper.getPermissionByRid"))
    })
    Role getByUid(@Param("uid")Long uid);
}
