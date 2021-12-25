package com.hr_java.mapper;

import com.hr_java.Model.entity.Serial;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 发放薪水 Mapper 接口
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-12
 */
@Mapper
public interface SerialMapper extends BaseMapper<Serial> {
    /**
     * 批量插入多条数据
     */
    @Insert({
            "<script>",
            "insert serial(uid, payrollID) values ",
            "<foreach collection='serial' item='serial' index='index' separator=','>",
            "(#{serial.uid}, #{serial.payrollID})",
            "</foreach>",
            "</script>"
    })
    int insertSerials(@Param(value="serial") List<Serial> list);
}
