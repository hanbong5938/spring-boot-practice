package com.github.demo.global.error.exception;


import com.github.demo.global.error.ErrorCode;

public class PasswordNotMatchedException extends BusinessException {
    public PasswordNotMatchedException() { super(ErrorCode.PASSWORD_IS_NOT_MATCHED); }
}
