package com.surgar.util;



import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {
    private boolean flag;//是否成功
    private Integer code;//返回码
    private String message;//返回消息
    private Object data;//返回数据

    public Result(boolean flag, Integer code, String message, Object data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data =  data;
    }

    public Result(boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public Result() {
        this.flag = true;
        this.code = StatusCode.OK;
        this.message = "操作成功!";
    }
    
    public static Result SUCCESS(Object data) {
        return new Result(true, StatusCode.OK, "success",  data);
    }
    public static Result FAIL() {
        return new Result(false, StatusCode.ERROR, "Error");
    }

    public boolean isFlag() {
        return flag;
    }
}
