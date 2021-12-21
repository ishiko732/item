package com.hr_java.mapper;

import com.hr_java.Model.entity.Department;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 * 机构 Mapper 接口
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-12
 */
@Mapper
public interface DepartmentMapper extends BaseMapper<Department> {

    @Select("select * from department where parentId is null")
    @Results({
        @Result(property = "deptID",column = "deptID"),
        @Result(property = "departments",javaType = List.class,column="deptID",
                many = @Many(select="com.hr_java.mapper.DepartmentMapper.selectByDeptOfParent"))
    })
    List<Department> selectByDepAll();

    @Select("select * from department where parentId = #{pid}")
    @Results({
            @Result(property = "deptID",column = "deptID"),
            @Result(property = "departments",javaType = List.class,column="deptID",
                    many = @Many(select="com.hr_java.mapper.DepartmentMapper.selectByDeptOfParent"))
    })
    List<Department> selectByDeptOfParent(@Param("pid")Integer pid);

    @Select("select * from department where deptID = #{pid} ")
    @Results({
            @Result(property = "deptID",column = "deptID"),
            @Result(property = "parentId",column = "parentId"),
            @Result(property = "departments",javaType = List.class,column="parentId",
                    many = @Many(select="com.hr_java.mapper.DepartmentMapper.selectByParentOfDep"))
    })
    Department reselectById(@Param("pid")Integer pid);
    @Select("select * from department where deptID = #{pid} ")
    @Results({
            @Result(property = "deptID",column = "deptID"),
            @Result(property = "parentId",column = "parentId"),
            @Result(property = "departments",javaType = List.class,column="parentId",
                    one = @One(select="com.hr_java.mapper.DepartmentMapper.selectByParentOfDep"))
    })
   Department selectByParentOfDep(@Param("pid")Integer pid);//反向找


}
