package com.hr_java.mapper;

import com.hr_java.Model.entity.Department;
import com.hr_java.Model.entity.Role;
import com.hr_java.Model.entity.Salary;
import com.hr_java.Model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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

    @Select("select * from user where name=#{name}")
    @Results({
            @Result(property = "uid",column = "uid"),
            @Result(property = "rid",column = "rid"),
            @Result(property = "pid",column = "pid"),
            @Result(property = "fid",javaType = Integer.class,column = "pid",
                    one=@One(select ="com.hr_java.mapper.PositionMapper.getFidByPid")),
            @Result(property = "role",javaType = Role.class,column="rid",
                    one = @One(select="com.hr_java.mapper.RoleMapper.getById")),
            @Result(property = "status",javaType = String.class,column = "uid",
                    one=@One(select ="com.hr_java.mapper.UserMapper.getStatusById"))
    })
    User getUserByName(@Param("name")String name);

    @Select("select * from user where uid=#{id}")
    @Results({
            @Result(property = "uid",column = "uid"),
            @Result(property = "rid",column = "rid"),
            @Result(property = "pid",column = "pid"),
            @Result(property = "fid",javaType = Integer.class,column = "pid",
                    one=@One(select ="com.hr_java.mapper.PositionMapper.getFidByPid")),
            @Result(property = "role",javaType = Role.class,column="rid",
                    one = @One(select="com.hr_java.mapper.RoleMapper.getById")),
            @Result(property = "status",javaType = String.class,column = "uid",
                    one=@One(select ="com.hr_java.mapper.UserMapper.getStatusById")),
            @Result(property = "jobTitles",javaType = String.class,column = "pid",
                    one=@One(select ="com.hr_java.mapper.JobTitlesMapper.getJobTitleByPid"))
    })
    User getById(@Param("id")Long id);


    @Select("select msg\n" +
            "from recheckUser\n" +
            "join status s on s.statusID = recheckUser.statusID\n" +
            "where uid=#{uid}")
    String getStatusById(@Param("uid")Long uid);

    @Select("select * from user")
    @Results({
            @Result(property = "uid",column = "uid"),
            @Result(property = "rid",column = "rid"),
            @Result(property = "pid",column = "pid"),
            @Result(property = "fid",javaType = Integer.class,column = "pid",
                    one=@One(select ="com.hr_java.mapper.PositionMapper.getFidByPid")),
            @Result(property = "role",javaType = Role.class,column="rid",
                    one = @One(select="com.hr_java.mapper.RoleMapper.getById")),
            @Result(property = "status",javaType = String.class,column = "uid",
                    one=@One(select ="com.hr_java.mapper.UserMapper.getStatusById"))
    })
    List<User> getUserList ();

    @Select({
            "<script>",
            "select uid from user join position p on p.pid = user.pid",
            "<where>" ,
            "<if test='fid !=null'>",
            "or fid=#{fid}",
            "</if>",
            "<if test='pcId !=null'>",
            "or pcId=#{pcId}",
            "</if>",
            "<if test='pid !=null'>",
            "or user.pid=#{pid}",
            "</if>",
            "<if test='recheckTime1 !=null and recheckTime2 !=null '>",
            "or (#{recheckTime1} >= registrantTime and registrantTime>= #{recheckTime2} )",
            "</if>",
            "</where>",
            "</script>"
    })
    Set<Long> selectUid(@Param("fid")String fid,
                         @Param("pcId")String pcId,
                         @Param("pid")String pid,
                         @Param("recheckTime1") LocalDateTime time1,
                         @Param("recheckTime2") LocalDateTime time2
    );

}
