package com.hr_java.service;

import com.hr_java.Model.entity.Transfer;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 调动临时表 服务类
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-23
 */
public interface TransferService extends IService<Transfer> {
    boolean transferUserByUID(Integer tid);
}
