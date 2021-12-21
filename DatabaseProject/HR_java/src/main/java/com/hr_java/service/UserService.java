package com.hr_java.service;

import com.hr_java.Model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-12
 */
public interface UserService extends IService<User> {
    Boolean register(User user);

    Boolean updateByUID(User user);

    User getUserByName(String name);

}
