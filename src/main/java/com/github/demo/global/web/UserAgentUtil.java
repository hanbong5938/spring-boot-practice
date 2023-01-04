package com.github.demo.global.web;

import io.netty.handler.codec.http.HttpHeaderNames;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)

public class UserAgentUtil {
    public static String getUserAgent(final HttpServletRequest request) {
        return request.getHeader(HttpHeaderNames.USER_AGENT.toString());
    }
}
