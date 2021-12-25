package com.hr_java.serviceImpl;

import com.hr_java.Model.entity.RecheckSerial;
import com.hr_java.Model.entity.Serial;
import com.hr_java.mapper.SerialMapper;
import com.hr_java.service.RecheckSerialService;
import com.hr_java.service.SerialService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
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
}
