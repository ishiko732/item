package com.hr_java.service;

import com.hr_java.Model.entity.Salary;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * <p>
 * 薪酬标准 服务类
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-12
 */
public interface SalaryService extends IService<Salary> {
    Salary getSalaryById(Long id);
    Set<Salary> getSalaryList();
    boolean insertSalary(Salary salary);
    Set<Salary> selectSalaryList(String salaryId,String salaryName, String MRUName, String registerName, String checkUserName, LocalDateTime time1, LocalDateTime time2);
}
