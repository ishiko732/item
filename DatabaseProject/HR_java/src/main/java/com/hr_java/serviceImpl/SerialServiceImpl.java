package com.hr_java.serviceImpl;

import com.hr_java.Model.VO.PayrollVO;
import com.hr_java.Model.VO.SerialVO;
import com.hr_java.Model.entity.RecheckSerial;
import com.hr_java.Model.entity.Serial;
import com.hr_java.mapper.SerialMapper;
import com.hr_java.service.RecheckSerialService;
import com.hr_java.service.SerialService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 发放薪水 服务实现类
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-12
 */
@Service
public class SerialServiceImpl extends ServiceImpl<SerialMapper, Serial> implements SerialService {
    @Autowired
    RecheckSerialService recheckSerialService;
    @Override
    public int insertSerials(List<Serial> list, Set<RecheckSerial> recheckSerials) {
        int i = getBaseMapper().insertSerials(list);
        if(i!=0){
            i=recheckSerialService.insertReSerials(recheckSerials);
        }
        return i;
    }

    @Override
    public Set<PayrollVO> selectPayrolls(@Nullable boolean isCascade) {
        Set<PayrollVO> payrollVOS = getBaseMapper().selectPayrolls();
        if (isCascade){
            for (PayrollVO payrollVO : payrollVOS) {
                Set<SerialVO> serialVOS = getBaseMapper().selectSerialByPayrollID(payrollVO.getPayrollID());
                payrollVO.setSerials(serialVOS);
            }
        }
        return payrollVOS;
    }

    @Override
    public Set<SerialVO> selectSerialByPayrollID(String payrollID) {
        return  getBaseMapper().selectSerialByPayrollID(payrollID);
    }

    @Override
    public Boolean updateSerialByID(Integer serialID, double bounty, double penalty) {
        if(bounty<0 || penalty<0){
            System.err.println("输入的数值不允许为负数！");
            return false;
        }
        if(!"正常".equals(getBaseMapper().getStatus(serialID))){ //待审核，驳回状态才能修改内部条目
            return getBaseMapper().updateSerialByID(serialID,bounty,penalty);
        }else{
            return false;
        }
    }

    @Override
    public boolean deleteSerialByID(Integer serialID) {
        if(!"正常".equals(getBaseMapper().getStatus(serialID))){//待审核，驳回状态才能修改内部条目
            return getBaseMapper().deleteSerialByID(serialID);
        }else{
            return false;
        }
    }
}
