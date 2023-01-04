package com.github.demo.global.web.resolver;

import com.github.demo.global.web.annotation.UserAgent;
import io.netty.handler.codec.http.HttpHeaderNames;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class UserAgentArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(final @NotNull MethodParameter methodParameter) {
        return methodParameter.getParameterAnnotation(UserAgent.class) != null;
    }

    @Override
    public Object resolveArgument(final @NotNull MethodParameter methodParameter,
                                  final ModelAndViewContainer modelAndViewContainer,
                                  final @NotNull NativeWebRequest nativeWebRequest,
                                  final WebDataBinderFactory webDataBinderFactory) {
        return ((HttpServletRequest) nativeWebRequest.getNativeRequest()).getHeader(HttpHeaderNames.USER_AGENT.toString());
    }
}
