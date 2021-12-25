package com.hr_java.mapper;

import com.hr_java.Model.entity.Transfer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 调动表 Mapper 接口
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-23
 */
@Mapper
public interface TransferMapper extends BaseMapper<Transfer> {
    @Update("update user set rid=#{rid} , pid=#{pid} where uid=#{uid}")
    boolean transferUserByUID(@Param("uid")Long uid,@Param("rid")Integer rid,@Param("pid")Integer pid);

}
