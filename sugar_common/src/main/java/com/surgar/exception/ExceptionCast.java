package com.surgar.exception;


import com.surgar.entity.ResultCode;

public class ExceptionCast {
    public static void cast(ResultCode resultCode) {
        throw new CustomException(resultCode);
    }
}
