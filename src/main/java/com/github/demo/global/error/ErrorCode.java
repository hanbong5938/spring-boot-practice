package com.github.demo.global.error;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {
    INVALID_INPUT_VALUE(400, "C001", "error_code.C001"),
    METHOD_NOT_ALLOWED(405, "C002", "error_code.C002"),
    ENTITY_NOT_FOUND(401, "C003", "error_code.C003"),
    INTERNAL_SERVER_ERROR(500, "C004", "error_code.C004"),
    INVALID_TYPE_VALUE(400, "C005", "error_code.C005"),
    HANDLE_ACCESS_DENIED(403, "C006", "error_code.C006"),
    SMS_SEND_FAIL(400, "C007", "error_code.C007"),
    INVALID_TOKEN(401, "C008", "error_code.C008"),
    ALREADY_APPLY(400, "C009", "error_code.C009"),
    EXPIRED_TOKEN(401, "C010", "error_code.C010"),
    MAIL_SEND_FAIL(400, "C011", "error_code.C011"),
    ACCESS_DENIED_IP_ADDRESS(400, "C012", "error_code.C012"),
    CAN_NOT_OVER_MAX_SIZE(400, "C013", "error_code.C013"),
    ACCOUNT_IS_LOCK(400, "U001", "error_code.U001"),
    ALREADY_USED_MAIL(400, "U002", "error_code.U002"),
    USER_NOT_ALLOW(400, "U003", "error_code.U003"),
    SIGNATURE_INVALID(417, "U004", "error_code.U004"),
    DEFAULT_FOLDER_DELETE_EXCEPTION(400, "U005", "error_code.U005"),
    PASSWORD_IS_NOT_MATCHED(400, "U006", "error_code.U006"),
    BIZ_FILE_NOT_FOUND(400, "U007", "error_code.U007"),
    PASSWORD_USER_ALREADY(400, "U008", "error_code.U008"),
    TITLE_USE_ALREADY(400, "U009", "error_code.U009"),
    ;
    /**
     * 초기 실행시 한번만 실행
     * code name 형태로 맵 생성
     */
    private static final Map<String, String> CODE_MAP = Collections.unmodifiableMap(
            Stream.of(values()).collect(Collectors.toMap(ErrorCode::getCode, ErrorCode::name)));
    private final String code;
    private final String message;
    private final int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    /**
     * 코드 생성
     */
    public static ErrorCode of(final String code) {
        return ErrorCode.valueOf(CODE_MAP.get(code));
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }
}
