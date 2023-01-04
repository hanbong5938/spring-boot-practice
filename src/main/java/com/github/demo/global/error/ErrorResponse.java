package com.github.demo.global.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.Builder;
import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

@Builder
public record ErrorResponse(String message, String code, List<FieldError> errors, int status) {

    public static ErrorResponse of(final ErrorCode code, final BindingResult bindingResult) {
        return ErrorResponse.builder()
                .message(code.getMessage())
                .code(code.getCode())
                .status(code.getStatus())
                .errors(FieldError.ofList(bindingResult)).build();
    }

    public static ErrorResponse of(final ErrorCode code) {
        return ErrorResponse.builder()
                .message(code.getMessage())
                .code(code.getCode())
                .status(code.getStatus())
                .errors(new ArrayList<>())
                .build();
    }

    public static ErrorResponse of(final ErrorCode code, final String message) {
        return ErrorResponse.builder()
                .message(message)
                .code(code.getCode())
                .status(code.getStatus())
                .errors(new ArrayList<>())
                .build();
    }

    public static ErrorResponse of(final ErrorCode code, final List<FieldError> errors) {
        return ErrorResponse.builder()
                .message(errors.stream().findFirst().orElseThrow().reason())
                .code(code.getCode())
                .status(code.getStatus())
                .errors(errors)
                .build();
    }

    public static ErrorResponse of(MethodArgumentTypeMismatchException e) {
        final String value = e.getValue() == null ? "" : e.getValue().toString();
        final List<FieldError> errors = FieldError.ofList(e.getName(), value, e.getErrorCode());
        return ErrorResponse.of(ErrorCode.INVALID_TYPE_VALUE, errors);
    }

    /**
     * get mapping 에서 사용하기 위해 생성
     * 제대로 출력 안될 가능성 보유
     */
    public static ErrorResponse of(ConstraintViolationException e) {
        final ConstraintViolation<?> constraintViolation = e.getConstraintViolations().stream().toList().get(0);
        final List<FieldError> errors = FieldError.ofList(
                constraintViolation.getPropertyPath().toString(),
                constraintViolation.getInvalidValue().toString()
                , constraintViolation.getMessage());
        return ErrorResponse.of(ErrorCode.INVALID_TYPE_VALUE, errors);
    }

    /**
     * json 형식 만들기 위해
     *
     * @return {"status":"INTERNAL_SERVER_ERROR","timestamp":"24-08-2021 10:03:31","message":"Unexpected error"}
     */
    public String convertToJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return mapper.writeValueAsString(this);
    }


    @Builder
    private record FieldError(String field, String value, String reason) {


        private static FieldError of(final String field, final String value, final String reason) {
            return FieldError.builder()
                    .field(field)
                    .value(value)
                    .reason(reason)
                    .build();
        }

        private static List<FieldError> ofList(final String field, final String value, final String reason) {
            List<FieldError> fieldErrors = new ArrayList<>();
            fieldErrors.add(FieldError.of(field, value, reason));
            return fieldErrors;
        }

        private static List<FieldError> ofList(final BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> FieldError.of(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .toList();
        }
    }

}
