package com.hr_java.mapper;

import com.hr_java.Model.entity.RecheckUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.Set;

/**
 * <p>
 * 复核用户 Mapper 接口
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-12
 */
@Mapper
public interface RecheckUserMapper extends BaseMapper<RecheckUser> {

    @Update("update recheckUser\n" +
            "set statusID =(select statusID from status where msg='已删除')\n" +
            "where uid=#{uid}")
    Boolean deleteUserByUid(@Param("uid")Long id);

    @Select("select rUserId from recheckUser where uid=#{uid}")
    Integer getRUidByUid(@Param("uid")Long uid);

}

