package com.github.demo.global.error.exception;

import com.github.demo.global.error.ErrorCode;

public class EntityNotFoundException extends BusinessException {
    public EntityNotFoundException(final String message) {
        super(message, ErrorCode.ENTITY_NOT_FOUND);
    }

    public EntityNotFoundException() {
        super(ErrorCode.ENTITY_NOT_FOUND);
    }
}
