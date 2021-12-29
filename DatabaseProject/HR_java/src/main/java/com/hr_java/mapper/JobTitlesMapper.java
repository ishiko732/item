package com.hr_java.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hr_java.Model.entity.JobTitles;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 职称 Mapper 接口
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-28
 */
@Mapper
public interface JobTitlesMapper extends BaseMapper<JobTitles> {
    @Select("select jt.name\n" +
            "from position\n" +
            "join jobTitles jT on position.jtId = jT.jtId\n" +
            "where pid=#{pid}\n")
    String getJobTitleByPid(@Param("pid") Integer pid);
}
