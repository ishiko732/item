package com.hr_java.mapper;

import com.hr_java.HrJavaApplication;
import com.hr_java.Model.VO.PayrollVO;
import com.hr_java.Model.VO.SerialVO;
import com.hr_java.Model.entity.Serial;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = HrJavaApplication.class)
class SerialMapperTest {
    @Autowired
    private SerialMapper serialMapper;
    @Test
    void selectPayrolls() {
        Set<PayrollVO> payrollVOS = serialMapper.selectPayrolls();
        System.out.println(payrollVOS);
    }

    @Test
    void selectSerialByPayrollID() {
        Set<SerialVO> serials = serialMapper.selectSerialByPayrollID("SG-202112000008");
        System.out.println(serials);
    }

    @Test
    void updateByid(){
        System.out.println(serialMapper.updateSerialByID(5, 100, -200));

    }
}