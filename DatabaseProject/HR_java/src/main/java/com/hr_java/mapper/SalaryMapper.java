package com.hr_java.mapper;

import com.hr_java.Model.entity.Role;
import com.hr_java.Model.entity.Salary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hr_java.Model.entity.Subsidy;
import org.apache.ibatis.annotations.*;

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

    @Insert("insert into salary(salaryId, registerName, MRUName, basePay)\n" +
            "values (#{salaryId},#{registerName},#{MRUName},#{basePay})")
    boolean insertSalary(Salary salary);

    @Select("select count(*)+1 from salary")
    Long getSalaryEndId();

}
