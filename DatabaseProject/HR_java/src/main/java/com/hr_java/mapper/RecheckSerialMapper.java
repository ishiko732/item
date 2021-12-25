package com.hr_java.mapper;

import com.hr_java.Model.entity.RecheckSerial;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hr_java.Model.entity.Serial;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 复核发放 Mapper 接口
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-12
 */
@Mapper
public interface RecheckSerialMapper extends BaseMapper<RecheckSerial> {
    /**
     * 批量插入多条数据
     */
    @Insert({
            "<script>",
            "insert recheckSerial(payrollID) values ",
            "<foreach collection='serial' item='serial' index='index' separator=','>",
            "(#{serial.payrollID})",
            "</foreach>",
            "</script>"
    })
    int insertReSerials(@Param(value="serial") Set<RecheckSerial> list);
}
