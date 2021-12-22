package com.hr_java.service;

import com.hr_java.Model.entity.RecheckUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 复核用户 服务类
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-12
 */
public interface RecheckUserService extends IService<RecheckUser> {
    boolean updateByRID(RecheckUser recheckUser);
    boolean deleteByUID(Long uid);
}
