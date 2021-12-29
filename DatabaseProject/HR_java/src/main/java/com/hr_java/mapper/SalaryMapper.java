package com.hr_java.mapper;

import com.hr_java.Model.entity.Role;
import com.hr_java.Model.entity.Salary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hr_java.Model.entity.Subsidy;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * <p>
 * 薪酬标准 Mapper 接口
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-12
 */
@Mapper
public interface SalaryMapper extends BaseMapper<Salary> {

    @Select("select * from salary_view where salaryId=#{id}")
    @Results({
            @Result(property = "salaryId",column = "salaryId"),
            @Result(property = "pi",column = "pi"),
            @Result(property = "mi",column = "mi"),
            @Result(property = "ui",column = "ui"),
            @Result(property = "housingFund",column = "housingFund"),
            @Result(property = "total_Salary",column = "total_Salary"),
            @Result(property = "subsidies",javaType = Set.class,column="salaryId",
                    many = @Many(select = "com.hr_java.mapper.SalaryMapper.getSubsidyBySalaryId"))
    })
    Salary getSalaryById(@Param("id")Long id);

    @Select("select * from salary_view")
    @Results({
            @Result(property = "salaryId",column = "salaryId"),
            @Result(property = "pi",column = "pi"),
            @Result(property = "mi",column = "mi"),
            @Result(property = "ui",column = "ui"),
            @Result(property = "housingFund",column = "housingFund"),
            @Result(property = "total_Salary",column = "total_Salary"),
            @Result(property = "subsidies",javaType = Set.class,column="salaryId",
                    many = @Many(select = "com.hr_java.mapper.SalaryMapper.getSubsidyBySalaryId"))
    })
    Set<Salary> getSalaryList();

    @Select("select * from subsidy where salaryId=#{salaryId}")
    Set<Subsidy> getSubsidyBySalaryId(@Param("salaryId")Long salaryId);

    @Options(useGeneratedKeys = true,keyProperty = "subsidyId",keyColumn = "subsidyId")
    @Insert("insert into subsidy(salaryId, subsidyName, money) \n" +
            "values (#{salaryId},#{subsidyName},#{money})")
    boolean insertSubsidies(Subsidy subsidy);

    @Insert("insert into salary(salaryId, registerName, MRUName, basePay,salaryName)\n" +
            "values (#{salaryId},#{registerName},#{MRUName},#{basePay},#{salaryName})")
    boolean insertSalary(Salary salary);

    @Select("select count(*)+1 from salary")
    Long getSalaryEndId();

    @Select({
            "<script>",
            "select salary_view.*,checkUserName,recheckTime from salary_view join reCheckSalary rCS on salary_view.salaryId = rCS.salaryId",
            "<where>" ,
            "<if test='salaryId !=null'>",
            "or CAST(salary_view.salaryId as char )  like concat('%',#{salaryId},'%')",
            "</if>",
            "<if test='salaryName !=null'>",
            "or salary_view.salaryName like concat('%',#{salaryName},'%')",
            "</if>",
            "<if test='MRUName !=null'>",
            "or salary_view.MRUName like concat('%',#{MRUName},'%')",
            "</if>",
            "<if test='registerName !=null'>",
            "or salary_view.registerName like concat('%',#{registerName},'%')",
            "</if>",
            "<if test='checkUserName !=null'>",
            "or checkUserName like concat('%',#{checkUserName},'%')",
            "</if>",
            "<if test='recheckTime1 !=null and recheckTime2 !=null '>",
            "or (#{recheckTime1} >= registerTime and registerTime>= #{recheckTime2} )",
            "</if>",
            "</where>",
            "</script>"
    })
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "total_Salary",column = "total_Salary"),
            @Result(property = "role",javaType = Role.class,column="role_id",
                    one = @One(select="mapper.RoleMapper.get")),
    })
    Set<Salary> selectSalaryList(@Param("salaryId")String salaryId,
                                 @Param("salaryName")String salaryName,
                                 @Param("MRUName")String MRUName,
                                 @Param("registerName")String registerName,
                                 @Param("checkUserName")String checkUserName,
                                 @Param("recheckTime1") LocalDateTime time1,
                                 @Param("recheckTime2") LocalDateTime time2
                                 );
}
