package com.hr_java.serviceImpl;

import com.hr_java.Model.entity.Permission;
import com.hr_java.mapper.PermissionMapper;
import com.hr_java.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-17
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

}
