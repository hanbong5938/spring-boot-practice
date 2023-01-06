package com.github.demo.global.log.custom;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.demo.constant.CustomLogConstant;
import com.github.demo.domain.user.common.domain.AccountType;
import com.github.demo.domain.user.common.dto.AccountDto;
import com.github.demo.global.security.provider.JwtTokenProvider;
import com.github.demo.global.web.IpAddressUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Aspect
@Slf4j
@RequiredArgsConstructor
public class ApiLog {

    private final ObjectMapper objectMapper;
    private final JwtTokenProvider jwtTokenProvider;

    private static final List<String> urlList = List.of("/api-docs", "/api-docs/swagger-config");

    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping) ||" +
            "@annotation(org.springframework.web.bind.annotation.PostMapping) ||" +
            "@annotation(org.springframework.web.bind.annotation.PutMapping) ||" +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public void logInfo() {
    }

    @Around(value = "logInfo()")
    public Object aspectGet(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return this.getObject(proceedingJoinPoint);
    }

    private Object getObject(final @NotNull ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        final HttpServletRequest request =
                ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                        .getRequest();
        final String url = request.getRequestURI();
        final ResponseEntity<?> result = (ResponseEntity<?>) proceedingJoinPoint.proceed();
        if (urlList.contains(url)) {
            return result;
        }


        final Signature signature = proceedingJoinPoint.getSignature();
        final Object body = this.getBody(proceedingJoinPoint, (MethodSignature) signature);
        final String http = request.getMethod();
        final Map<String, String[]> parameterMap = request.getParameterMap();
        final Map<String, Boolean> hasData = new HashMap<>();
        hasData.put(CustomLogConstant.BODY, body != null);
        hasData.put(CustomLogConstant.PARAMS, parameterMap.size() > 0);
        final Collection<Part> parts = this.checkFile(request, hasData);
        final UUID userId = this.getUuid(request, result);
        final AccountType accountType = this.getAccountType(request, result);
        final String ip = IpAddressUtil.getClientIp(request);
        log.info(this.getLogFormat(hasData),
                http, url, signature.getDeclaringTypeName(),
                signature.getName(), ip,
                accountType,
                userId,
                this.getStr(this.toString(parameterMap), this.getKey(hasData)),
                this.getStr(this.getBody(body, http), hasData.get(CustomLogConstant.BODY)),
                this.getStr(this.getValue(parts),
                        hasData.get(CustomLogConstant.FILE))
        );
        return result;
    }

    private String getValue(final @NotNull Collection<Part> parts) throws JsonProcessingException {
        return !parts.isEmpty() ? this.toString(parts.stream().map(Part::getSubmittedFileName).collect(Collectors.toSet()))
                : CustomLogConstant.NO_DATA;
    }

    private boolean getKey(final @NotNull Map<String, Boolean> hasData) {
        return Boolean.TRUE.equals(hasData.get(CustomLogConstant.FILE)) ? hasData.get(CustomLogConstant.BODY)
                : hasData.get(CustomLogConstant.PARAMS);
    }

    private @Nullable Object getBody(final ProceedingJoinPoint proceedingJoinPoint, final @NotNull MethodSignature methodSignature) {
        final Annotation[][] annotationMatrix = methodSignature.getMethod().getParameterAnnotations();
        int index = -1;
        for (final Annotation[] annotations : annotationMatrix) {
            index++;
            for (final Annotation annotation : annotations) {
                if (annotation instanceof RequestBody) {
                    return proceedingJoinPoint.getArgs()[index];
                }
            }
        }
        return null;
    }

    private @NotNull String getLogFormat(final @NotNull Map<String, Boolean> hasData) {
        final String replacement = " {}";
        return String.join(CustomLogConstant.LOG_PARAM, CustomLogConstant.HTTP, CustomLogConstant.URI,
                        CustomLogConstant.API, CustomLogConstant.METHOD, CustomLogConstant.IP,
                        CustomLogConstant.ACCOUNT_TYPE, CustomLogConstant.USER,
                        Boolean.TRUE.equals(hasData.get(CustomLogConstant.PARAMS)) ?
                                CustomLogConstant.PARAMS : CustomLogConstant.NO_DATA,
                        Boolean.TRUE.equals(hasData.get(CustomLogConstant.BODY)) ?
                                CustomLogConstant.BODY : CustomLogConstant.NO_DATA,
                        Boolean.TRUE.equals(hasData.get(CustomLogConstant.FILE)) ?
                                CustomLogConstant.FILE : CustomLogConstant.NO_DATA,
                        CustomLogConstant.NO_DATA
                )
                .replace(" " + CustomLogConstant.LOG_PARAM, replacement)
                .replace("{}" + " : {},", replacement);
    }

    private String getStr(final String str, final boolean hasData) {
        return hasData ? str : CustomLogConstant.NO_DATA;
    }

    private Collection<Part> checkFile(final @NotNull HttpServletRequest request,
                                       final Map<String, Boolean> hasData) throws IOException, ServletException {
        final String contentType = request.getContentType();
        if (contentType != null && contentType.startsWith("CustomLogConstant.MULTIPART")) {
            final Collection<Part> parts = request.getParts();
            hasData.put(CustomLogConstant.FILE, parts != null);
            return parts;
        } else {
            hasData.put(CustomLogConstant.FILE, false);
        }
        return Collections.emptyList();
    }

    private @Nullable UUID getUuid(final HttpServletRequest request, final ResponseEntity<?> result) {
        final UUID userId = this.jwtTokenProvider.getId(request);
        if (userId == null) {
            if (result == null) {
                return null;
            }
            if (result.getBody() instanceof AccountDto.TokenResponse response) {
                return jwtTokenProvider.getId(response.accessToken());
            }
        }
        return userId;
    }

    private @Nullable AccountType getAccountType(final HttpServletRequest request, final ResponseEntity<?> result) {
        final AccountType accountType = this.jwtTokenProvider.getAccountType(request);
        if (accountType == null) {
            if (result == null) {
                return null;
            }

            if (result.getBody() instanceof AccountDto.TokenResponse response) {
                return jwtTokenProvider.getAccountType(response.accessToken());
            }
        }
        return accountType;
    }

    private String getBody(final Object body, final String http) throws JsonProcessingException {
        return !Objects.equals(http, HttpMethod.GET.name()) && body != null ?
                this.toString(body) : "";
    }

    private String toString(final Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
