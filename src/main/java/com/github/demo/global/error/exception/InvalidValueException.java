package com.github.demo.global.error.exception;

import com.github.demo.global.error.ErrorCode;

public class InvalidValueException extends BusinessException {

    /**
     * 데이터 잘못된 경우
     *
     * @param value 처리할 내용
     */
    public InvalidValueException(final String value, final ErrorCode errorCode) {
        super(value, errorCode);
    }
}
