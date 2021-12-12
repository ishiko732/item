package com.hr_java;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hr_java.mapper")
public class HrJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(HrJavaApplication.class, args);
    }

}
