package com.hr_java.serviceImpl;


import com.hr_java.Model.entity.JobTitles;
import com.hr_java.mapper.JobTitlesMapper;
import com.hr_java.service.JobTitlesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 职称 服务实现类
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-28
 */
@Service
public class JobTitlesServiceImpl extends ServiceImpl<JobTitlesMapper, JobTitles> implements JobTitlesService {

}
