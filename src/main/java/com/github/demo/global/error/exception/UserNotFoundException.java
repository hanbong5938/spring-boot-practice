package com.github.demo.global.error.exception;


import com.github.demo.global.error.ErrorCode;

public class UserNotFoundException extends BusinessException {

    public UserNotFoundException() {
        super(ErrorCode.ENTITY_NOT_FOUND);
    }
}
