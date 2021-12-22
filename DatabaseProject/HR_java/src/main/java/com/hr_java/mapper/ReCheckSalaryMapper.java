package com.hr_java.mapper;

import com.hr_java.Model.entity.ReCheckSalary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 复核薪水标准 Mapper 接口
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-12
 */
@Mapper
public interface ReCheckSalaryMapper extends BaseMapper<ReCheckSalary> {
    @Update("update recheckSalary\n" +
            "set statusID =(select statusID from status where msg='已删除')\n" +
            "where salaryId=#{salaryId}")
    Boolean deleteUserBySalaryId(@Param("salaryId")Long salaryId);
}
