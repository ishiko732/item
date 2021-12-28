package com.hr_java.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hr_java.Model.entity.Position;
import com.hr_java.Model.entity.PositionClassification;

import java.util.List;

/**
 * <p>
 * 职位类别 服务类
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-28
 */
public interface PositionClassificationService extends IService<PositionClassification> {
    List<Position> getByPCid(Integer PCid);
}
