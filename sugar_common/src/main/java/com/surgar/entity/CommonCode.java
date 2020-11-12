package com.surgar.entity;

import lombok.ToString;



@ToString
public enum CommonCode implements ResultCode{

    SUCCESS(true,20000,"操作成功！"),
    FAIL(false,20001,"操作失败！"),
    UNAUTHENTICATED(false,20001,"此操作需要登陆系统！"),
    UNAUTHORISE(false,20003,"权限不足，无权操作！"),
    SERVER_ERROR(false,20001,"抱歉，系统繁忙，请稍后重试！"),
    INVALID_PARAM(false, 20001 ,"非法参数" ),
    SMS_CODE_ERROR(false, 20001 ,"验证码错误!" );
//    private static ImmutableMap<Integer, CommonCode> codes ;
    //操作是否成功
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;
    private CommonCode(boolean success,int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean success() {
        return success;
    }
    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }


}
