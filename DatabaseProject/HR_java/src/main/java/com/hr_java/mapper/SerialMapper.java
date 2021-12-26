package com.hr_java.mapper;

import com.hr_java.Model.VO.PayrollVO;
import com.hr_java.Model.VO.SerialVO;
import com.hr_java.Model.entity.Serial;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 发放薪水 Mapper 接口
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-12
 */
@Mapper
public interface SerialMapper extends BaseMapper<Serial> {
    /**
     * 批量插入多条数据
     */
    @Insert({
            "<script>",
            "insert serial(uid, payrollID) values ",
            "<foreach collection='serial' item='serial' index='index' separator=','>",
            "(#{serial.uid}, #{serial.payrollID})",
            "</foreach>",
            "</script>"
    })
    int insertSerials(@Param(value="serial") List<Serial> list);

    @Select("select payrollID,d1Name,D2Name,d3Name,count(*) as 'count',sum(total_Salary)+sum(bounty)-sum(penalty) as 'sum'\n" +
            "from serial\n" +
            "         join user u on u.uid = serial.uid\n" +
            "         join dep3 on d3ID=( select fid from position where pid =u.pid)\n" +
            "         join salary_view on salaryId in (select salaryId from position where u.pid=position.pid)\n" +
            "where statusId=(select statusId from status where msg='发放')\n"+
            "group by payrollID;")
    @Results({
            @Result(property = "payrollID",column = "payrollID"),
            @Result(property = "dep1",column = "d1Name"),
            @Result(property = "dep2",column = "d2Name"),
            @Result(property = "dep3",column = "d3Name"),
            @Result(property = "count",column = "count"),
            @Result(property = "sum",column = "sum"),
    })
    Set<PayrollVO> selectPayrolls ();



    @Select("select payrollID,serialID,u.uid,u.name,basePay, subsidy, pi, mi, ui, housingFund, total_Salary,bounty,penalty,salaryId\n" +
            "from serial\n" +
            "join user u on u.uid = serial.uid\n" +
            "join salary_view on salaryId=(select salaryId from position where u.pid=position.pid)\n" +
            "where payrollID=#{payrollID} and statusId=(select statusId from status where msg='发放')")
    @Results({
            @Result(property = "payrollID",column = "payrollID"),
            @Result(property = "serialID",column = "serialID"),
            @Result(property = "salaryId",column = "salaryId"),
            @Result(property = "subsidies",javaType = Set.class,column="salaryId",
                    many = @Many(select = "com.hr_java.mapper.SalaryMapper.getSubsidyBySalaryId")),
    })
    Set<SerialVO> selectSerialByPayrollID(@Param("payrollID")String payrollID);

    @Update("update serial\n" +
            "set bounty=#{bounty},penalty=#{penalty}\n" +
            "where serialID=#{serialID}")
    boolean updateSerialByID(@Param("serialID")Integer serialID,@Param("bounty")double bounty,@Param("penalty") double penalty);

    @Update("update serial\n" +
            "set statusId=(select statusId from status where msg='不发放')\n" +
            "where serialID=#{serialID}")
    boolean deleteSerialByID(@Param("serialID")Integer serialID);

}
