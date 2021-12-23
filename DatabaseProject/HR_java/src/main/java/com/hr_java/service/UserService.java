package com.hr_java.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hr_java.Model.entity.User;
import org.springframework.lang.Nullable;

import java.util.Map;

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
    Map<String,String> login(String username, String password);
    void logout();

    Boolean updateByUID(User user);
    Boolean updateByUID_transfer(User user);

    User getUserByName(String name);

    User getById(Long id);

}
