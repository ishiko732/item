package com.hr_java.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hr_java.Model.entity.Position;
import com.hr_java.Model.entity.PositionClassification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 职位类别 Mapper 接口
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-28
 */
@Mapper
public interface PositionClassificationMapper extends BaseMapper<PositionClassification> {
    @Select("select * from position where pcid=#{id}")
    List<Position> getByPCid(@Param("id")Integer id);
}
