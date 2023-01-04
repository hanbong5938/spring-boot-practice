package com.github.demo.global.security.handler;


import com.github.demo.constant.SecurityConstant;
import com.github.demo.global.error.ErrorCode;
import com.github.demo.global.error.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(final @NotNull HttpServletRequest request, final HttpServletResponse response,
                         final AuthenticationException authException) throws IOException {
        String exception = (String) request.getAttribute(SecurityConstant.EXCEPTION);


        log.debug("log: exception: {} ", exception);

        //토큰 없는 경우
        if (exception == null) {
            setResponse(response, ErrorCode.INVALID_TOKEN);
        } else {
            setResponse(response, ErrorCode.of(exception));
        }
    }

    /**
     * 한글 출력을 위해 getWriter() 사용
     */
    private void setResponse(final @NotNull HttpServletResponse response,
                             final @NotNull ErrorCode errorCode) throws IOException {
        response.setStatus(errorCode.getStatus());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // json 형태로 변환
        String json = ErrorResponse.of(errorCode).convertToJson();
        response.getWriter().write(json);
    }

}
