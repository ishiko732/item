package com.hr_java.Model.VO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserJWT {
    private Long uid;
    private String name;
}
