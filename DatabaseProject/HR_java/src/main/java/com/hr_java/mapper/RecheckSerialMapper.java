package com.hr_java.mapper;

import com.hr_java.Model.VO.PayrollVO;
import com.hr_java.Model.entity.RecheckSerial;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hr_java.Model.entity.RecheckUser;
import com.hr_java.Model.entity.Serial;
import com.hr_java.Model.entity.User;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 复核发放 Mapper 接口
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-12
 */
@Mapper
public interface RecheckSerialMapper extends BaseMapper<RecheckSerial> {
    /**
     * 批量插入多条数据
     */
    @Insert({
            "<script>",
            "insert recheckSerial(payrollID) values ",
            "<foreach collection='serial' item='serial' index='index' separator=','>",
            "(#{serial.payrollID})",
            "</foreach>",
            "</script>"
    })
    int insertReSerials(@Param(value="serial") Set<RecheckSerial> list);

    @Update("update recheckSerial\n" +
            "set statusID =(select statusID from status where msg='已删除'),message=#{message},checkUserName=#{checkUserName},recheckTime=#{recheckTime}\n" +
            "where payrollID=#{payrollID}")
    Boolean deletePayrollById(@Param("payrollID")String payrollID,
                              @Param("message")String message,
                              @Param("checkUserName")String checkUserName,
                              @Param("recheckTime") LocalDateTime recheckTime);

    @Update("update recheckSerial\n" +
            "set statusID =(select statusID from status where msg='已删除'),message=#{message},checkUserName=#{checkUserName},recheckTime=#{recheckTime}\n" +
            "where rSerialId=#{rSerialId}")
    Boolean deleteById(@Param("rSerialId")Integer rSerialId,
                              @Param("message")String message,
                              @Param("checkUserName")String checkUserName,
                              @Param("recheckTime") LocalDateTime recheckTime);

    @Select("select rSerialId from recheckSerial where payrollID=#{payrollID}")
    Integer getRSerialIdByPayrollId(@Param("payrollID")String payrollID);

    @Select("select * from recheckSerial where rSerialId=#{rSerialId}")
    @Results({
            @Result(property = "rSerialId",column = "rSerialId"),
            @Result(property = "payrollID",column = "payrollID"),
            @Result(property = "payroll",javaType = PayrollVO.class,column = "payrollID",
                    one=@One(select ="com.hr_java.mapper.SerialMapper.selectPayrollsById"))
    })
    RecheckSerial getById(@Param("rSerialId")Integer rSerialId);

    @Select("select * from recheckSerial")
    @Results({
            @Result(property = "rSerialId",column = "rSerialId"),
            @Result(property = "payrollID",column = "payrollID"),
            @Result(property = "payroll",javaType = PayrollVO.class,column = "payrollID",
                    one=@One(select ="com.hr_java.mapper.SerialMapper.selectPayrollsById"))
    })
    Set<RecheckSerial> getList();
}
