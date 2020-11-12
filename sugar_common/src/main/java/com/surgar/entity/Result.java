package com.surgar.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    //是否成功
    private boolean flag;
    //返回码
    private Integer code;
    //返回信息
    private String message;
    //返回数据
    private Object data;

    public Result(boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public Result(ResultCode resultCode) {
        this.flag = resultCode.success();
        this.code = resultCode.code();
        this.message = resultCode.message();
    }

    public Result(ResultCode resultCode, Object object) {
        this.flag = resultCode.success();
        this.code = resultCode.code();
        this.message = resultCode.message();
        this.data = object;
    }

    public static Result SUCCESS() {
        return new Result(true, StatusCode.OK, "成功");
    }
    public static Result FAIL() {
        return new Result(false, StatusCode.ERROR, "失败");
    }

}
