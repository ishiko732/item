package com.hr_java.service;

import com.hr_java.Model.VO.PayrollVO;
import com.hr_java.Model.VO.SerialVO;
import com.hr_java.Model.entity.RecheckSerial;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hr_java.Model.entity.Serial;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 复核发放 服务类
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-12
 */
public interface RecheckSerialService extends IService<RecheckSerial> {
    int insertReSerials(Set<RecheckSerial> list);

    Set<RecheckSerial> getList();

    RecheckSerial getById(Integer rSerialId);

    Integer getRSerialIdByPayrollId(String payrollID);

    boolean deletePayrollById(String payrollID, String message);

    boolean deleteById(Integer rSerialId, String message);
}
