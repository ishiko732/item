package com.hr_java.mapper;

import com.hr_java.Model.entity.Role;
import com.hr_java.Model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-12
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select count(*) from user where uid>#{id1} and uid<#{id2}")
    Long userEndID(@Param("id1")String id1,@Param("id2")String id);

    @Select("select* from user where name=#{name}")
    @Results({
            @Result(property = "uid",column = "uid"),
            @Result(property = "role",javaType = Role.class,column="rid",
                    one = @One(select="com.hr_java.mapper.RoleMapper.getById"))
    })
    User getUserByName(@Param("name")String name);

}
