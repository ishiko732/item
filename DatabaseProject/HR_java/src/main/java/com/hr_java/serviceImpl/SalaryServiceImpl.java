package com.hr_java.serviceImpl;

import com.hr_java.Model.entity.ReCheckSalary;
import com.hr_java.Model.entity.RecheckUser;
import com.hr_java.Model.entity.Salary;
import com.hr_java.Model.entity.Subsidy;
import com.hr_java.mapper.SalaryMapper;
import com.hr_java.security.JWTUtil;
import com.hr_java.service.ReCheckSalaryService;
import com.hr_java.service.SalaryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

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
    @Autowired
    ReCheckSalaryService reCheckSalaryService;

    @Override
    public Salary getSalaryById(Long id) {
        return getBaseMapper().getSalaryById(id);
    }

    @Override
    public Set<Salary> getSalaryList() {
        return getBaseMapper().getSalaryList();
    }

    @Override
    public boolean insertSalary(Salary salary) {
        Long salaryId = getBaseMapper().getSalaryEndId();
        if(Objects.isNull(salary.getSalaryName())){
            salary.setSalaryName("新标准"+salaryId);
        }
        salary.setSalaryId(salaryId);
        boolean b = getBaseMapper().insertSalary(salary);
        boolean s=true;
        boolean ret=false;
        if(b){
            Set<Subsidy> subsidies=salary.getSubsidies();
            for (Subsidy subsidy : subsidies) {
                subsidy.setSalaryId(salaryId);
                s=getBaseMapper().insertSubsidies(subsidy);
            }
            ReCheckSalary reCheckSalary=new ReCheckSalary();
            reCheckSalary.setSalaryId(salaryId);
            reCheckSalary.setStatusID(0);
            ret = reCheckSalaryService.getBaseMapper().insert(reCheckSalary)==1;
        }
        return b&&s&&ret;
    }

    @Override
    public Set<Salary> selectSalaryList(String salaryId, String salaryName, String MRUName, String registerName, String checkUserName, LocalDateTime time1, LocalDateTime time2) {
        return getBaseMapper().selectSalaryList(salaryId, salaryName, MRUName, registerName, checkUserName, time1, time2);
    }
}
