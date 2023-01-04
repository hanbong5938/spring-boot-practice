package com.github.demo.global.error.exception;

import com.github.demo.global.error.ErrorCode;

public class TypeInvalidException extends BusinessException {

    public TypeInvalidException() { super(ErrorCode.INVALID_TYPE_VALUE); }
}
