package com.github.demo.global.error.exception;


import com.github.demo.global.error.ErrorCode;
import jakarta.persistence.PersistenceException;

public abstract class BusinessException extends PersistenceException {
    private final ErrorCode errorCode;

    BusinessException(final String message, final ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    BusinessException(final ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
