package com.github.demo.global.error.exception;


import com.github.demo.global.error.ErrorCode;

import java.util.UUID;

public class UserNotAllowException extends BusinessException {
    /**
     * UserNoAllowException 정의
     * 미승인 상태인 경우
     *
     * @param uuid 객체
     */
    public UserNotAllowException(final UUID uuid) {
        super(uuid.toString(), ErrorCode.USER_NOT_ALLOW);
    }

    /**
     * UserNoAllowException 정의
     * 미승인 상태인 경우
     *
     * @param uuid str
     */
    public UserNotAllowException(final String uuid) {
        super(uuid, ErrorCode.USER_NOT_ALLOW);
    }

    public UserNotAllowException() {
        super(ErrorCode.USER_NOT_ALLOW);
    }

}
