package com.github.demo.global.error.exception;


import com.github.demo.global.error.ErrorCode;

public abstract class BusinessException extends RuntimeException {
    private final ErrorCode errorCode;

    BusinessException(final String message, final ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    BusinessException(final ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    BusinessException() {
        super();
        this.errorCode = null;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
