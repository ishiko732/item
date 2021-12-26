package com.hr_java.service;

import com.hr_java.Model.VO.PayrollVO;
import com.hr_java.Model.VO.SerialVO;
import com.hr_java.Model.entity.RecheckSerial;
import com.hr_java.Model.entity.Serial;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 发放薪水 服务类
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-12
 */
public interface SerialService extends IService<Serial> {
    int insertSerials(List<Serial> list, Set<RecheckSerial> recheckSerials);
    Set<PayrollVO> selectPayrolls (@Nullable boolean isCascade);
    Set<SerialVO> selectSerialByPayrollID(String payrollID);
    Boolean updateSerialByID(Integer serialID,double bounty,double penalty);
    boolean deleteSerialByID(Integer serialID);
}
