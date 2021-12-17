package com.hr_java.serviceImpl;

import com.hr_java.entity.Department;
import com.hr_java.mapper.DepartmentMapper;
import com.hr_java.service.DepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 机构 服务实现类
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-12
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {
public List<Department> selectByDepAll(){//获取部门列表
    return getBaseMapper().selectByDepAll();
}

    @Override
    public Department reSelectByDep(Integer id) {

        return getBaseMapper().reselectById(id);
    }
}
