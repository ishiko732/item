package com.hr_java.serviceImpl;

import com.hr_java.Model.entity.ReCheckSalary;
import com.hr_java.mapper.ReCheckSalaryMapper;
import com.hr_java.service.ReCheckSalaryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 复核薪水标准 服务实现类
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-12
 */
@Service
public class ReCheckSalaryServiceImpl extends ServiceImpl<ReCheckSalaryMapper, ReCheckSalary> implements ReCheckSalaryService {
    @Override
    public boolean deleteBySalaryId(Long salaryId) {
        return getBaseMapper().deleteUserBySalaryId(salaryId);
    }
}
