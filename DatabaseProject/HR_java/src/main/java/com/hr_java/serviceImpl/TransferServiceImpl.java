package com.hr_java.serviceImpl;

import com.hr_java.Model.entity.Transfer;
import com.hr_java.mapper.TransferMapper;
import com.hr_java.service.TransferService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr_java.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 调动临时表 服务实现类
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-23
 */
@Service
public class TransferServiceImpl extends ServiceImpl<TransferMapper, Transfer> implements TransferService {
    @Override
    public boolean transferUserByUID(Integer tid) {
        Transfer transfer = getById(tid);
        System.err.println(transfer);
        return getBaseMapper().transferUserByUID(transfer.getUid(), transfer.getRid(), transfer.getPid());
    }
}
