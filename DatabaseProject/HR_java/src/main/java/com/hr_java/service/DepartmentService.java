package com.hr_java.service;

import com.hr_java.Model.entity.Department;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 机构 服务类
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-12
 */
public interface DepartmentService extends IService<Department> {
   List<Department> selectByDepAll();

   Department reSelectByDep(Integer id);

}
