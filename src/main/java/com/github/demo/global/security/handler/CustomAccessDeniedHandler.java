package com.github.demo.global.security.handler;

import com.github.demo.global.error.ErrorCode;
import com.github.demo.global.error.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * 권한 없는 경우 처리
     */
    @Override
    public void handle(final HttpServletRequest request,
                       final @NotNull HttpServletResponse response,
                       final AccessDeniedException accessDeniedException) throws IOException {
        final ErrorCode errorCode = ErrorCode.HANDLE_ACCESS_DENIED;
        response.setStatus(errorCode.getStatus());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // json 형태로 변환
        final String json = ErrorResponse.of(errorCode).convertToJson();
        response.getWriter().write(json);
    }
}
