package com.hr_java.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum HttpCodeEnum {
    OK(200,"OK"),
    CREATEOK(201,"CREATEOK"),
    REDIRECT(301,"REDIRECT"),
    PARAM_ERROR(400,"PARAM_ERROR"),
    NOT_FOUND(404,"NOT_FOUND"),
    SERVER_ERROR(500,"SERVER_ERROR");
    private Integer httpCode;
    private String httpMsg;
    HttpCodeEnum(Integer httpCode,String httpMsg){
        this.httpCode=httpCode;
        this.httpMsg=httpMsg;
    }
}
