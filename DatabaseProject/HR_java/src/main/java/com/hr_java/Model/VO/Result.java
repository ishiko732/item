package com.hr_java.Model.VO;

import com.google.gson.Gson;
import com.hr_java.utils.HttpCodeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class Result implements Serializable {
    private Integer code;
    private String msg;
    private Object data;

    public static Result succ(Object data) {
        Result m = new Result();
        m.setCode(HttpCodeEnum.OK.getHttpCode());
        m.setMsg(HttpCodeEnum.OK.getHttpMsg());
        m.setData(data);
        return m;
    }
    public static Result succ(HttpCodeEnum httpCodeEnum, Object data) {
        Result m = new Result();
        m.setCode(httpCodeEnum.getHttpCode());
        m.setMsg(httpCodeEnum.getHttpMsg());
        m.setData(data);
        return m;
    }
    public static Result fail(String mess) {
        Result m = new Result();
        m.setCode(HttpCodeEnum.PARAM_ERROR.getHttpCode());
        m.setMsg(HttpCodeEnum.PARAM_ERROR.getHttpMsg());
        m.setData(null);
        return m;
    }
    public static Result fail(HttpCodeEnum httpCodeEnum, Object data) {
        Result m = new Result();
        m.setCode(httpCodeEnum.getHttpCode());
        m.setMsg(httpCodeEnum.getHttpMsg());
        m.setData(data);
        return m;
    }
    public static Result fail(Integer code,String mess) {
        Result m = new Result();
        m.setCode(code);
        m.setMsg(mess);
        m.setData(null);
        return m;
    }

    @Override
    public String toString() {
        Map<String,Object> map=new HashMap<>();
        map.put("code",code);
        map.put("msg",msg);
        map.put("data",data);
        return new Gson().toJson(map);
    }
}