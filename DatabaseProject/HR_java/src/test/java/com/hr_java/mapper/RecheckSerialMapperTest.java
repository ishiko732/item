package com.hr_java.mapper;

import com.hr_java.HrJavaApplication;
import com.hr_java.Model.entity.RecheckSerial;
import com.hr_java.Model.entity.RecheckUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(classes = HrJavaApplication.class)
class RecheckSerialMapperTest {
    @Autowired
    private RecheckSerialMapper recheckSerialMapper;
    @Test
    void deletePayrollById() {
        Boolean payroll = recheckSerialMapper.deletePayrollById("SG-202112000008","不允许：相同条目","superAdmin",
                LocalDateTime.now());
        System.out.println(payroll);
    }

    @Test
    void getRSerialIdByPayrollId() {
        Integer rSerialIdByPayrollId = recheckSerialMapper.getRSerialIdByPayrollId("SG-202112000008");
        System.out.println(rSerialIdByPayrollId);
    }

    @Test
    void getById() {
        RecheckSerial recheckSerial = recheckSerialMapper.getById(1);
        System.out.println(recheckSerial);
    }
}