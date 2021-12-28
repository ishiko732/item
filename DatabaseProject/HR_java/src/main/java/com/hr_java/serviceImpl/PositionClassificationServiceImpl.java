package com.hr_java.serviceImpl;

import com.hr_java.Model.entity.Position;
import com.hr_java.Model.entity.PositionClassification;
import com.hr_java.mapper.PositionClassificationMapper;
import com.hr_java.service.PositionClassificationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 职位类别 服务实现类
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-28
 */
@Service
public class PositionClassificationServiceImpl extends ServiceImpl<PositionClassificationMapper, PositionClassification> implements PositionClassificationService {
    @Override
    public List<Position> getByPCid(Integer PCid) {
        return getBaseMapper().getByPCid(PCid);
    }
}
