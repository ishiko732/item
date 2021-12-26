package com.hr_java.serviceImpl;

import com.hr_java.Model.entity.RecheckSerial;
import com.hr_java.mapper.RecheckSerialMapper;
import com.hr_java.security.JWTUtil;
import com.hr_java.service.RecheckSerialService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 复核发放 服务实现类
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-12
 */
@Service
public class RecheckSerialServiceImpl extends ServiceImpl<RecheckSerialMapper, RecheckSerial> implements RecheckSerialService {
    @Override
    public int insertReSerials(Set<RecheckSerial> list) {
        return getBaseMapper().insertReSerials(list);
    }

    @Override
    public Set<RecheckSerial> getList() {
        return getBaseMapper().getList();
    }

    @Override
    public RecheckSerial getById(Integer rSerialId) {
        return getBaseMapper().getById(rSerialId);
    }

    @Override
    public Integer getRSerialIdByPayrollId(String payrollID) {
        return getBaseMapper().getRSerialIdByPayrollId(payrollID);
    }

    @Override
    public boolean deletePayrollById(String payrollID, String message) {
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        String name = JWTUtil.getUsername(token);//操作员的姓名
        LocalDateTime time= LocalDateTime.now();
        return getBaseMapper().deletePayrollById(payrollID,message,name,time);
    }

    @Override
    public boolean deleteById(Integer rSerialId, String message) {
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        String name = JWTUtil.getUsername(token);//操作员的姓名
        LocalDateTime time= LocalDateTime.now();
        return getBaseMapper().deleteById(rSerialId,message,name,time);
    }

    @Override
    public boolean updateById(RecheckSerial entity) {
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        String name = JWTUtil.getUsername(token);//操作员的姓名
        LocalDateTime time= LocalDateTime.now();
        entity.setCheckUserName(name);
        entity.setRecheckTime(time);
        entity.setPayrollID(null);//不允许更新payrollID
        return super.updateById(entity);
    }
}
