package com.hr_java.mapper;

import com.hr_java.Model.entity.Salary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

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

}
