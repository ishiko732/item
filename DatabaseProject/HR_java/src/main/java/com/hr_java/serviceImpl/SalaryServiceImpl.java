package com.hr_java.serviceImpl;

import com.hr_java.entity.Salary;
import com.hr_java.mapper.SalaryMapper;
import com.hr_java.service.SalaryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 薪酬标准 服务实现类
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-12
 */
@Service
public class SalaryServiceImpl extends ServiceImpl<SalaryMapper, Salary> implements SalaryService {

}
