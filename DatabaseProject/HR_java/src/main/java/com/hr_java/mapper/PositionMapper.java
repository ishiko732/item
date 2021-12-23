package com.hr_java.mapper;

import com.hr_java.Model.entity.Position;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 职位 Mapper 接口
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-12
 */
@Mapper
public interface PositionMapper extends BaseMapper<Position> {
    @Select("select fid from position where pid=#{pid}")
    Integer getFidByPid(@Param("pid")Integer pid);
}
