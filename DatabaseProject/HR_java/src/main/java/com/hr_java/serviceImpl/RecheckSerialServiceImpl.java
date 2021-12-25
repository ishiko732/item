package com.hr_java.serviceImpl;

import com.hr_java.Model.entity.RecheckSerial;
import com.hr_java.mapper.RecheckSerialMapper;
import com.hr_java.service.RecheckSerialService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
}
