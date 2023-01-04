package com.github.demo.global.security.filter;

import com.github.demo.constant.SecurityConstant;
import com.github.demo.global.error.ErrorCode;
import com.github.demo.global.error.exception.ExpiredTokenException;
import com.github.demo.global.security.provider.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Request 로 들어오는 Jwt Token 의 유효성을 검증 (jwtTokenProvider.validateToken) 하는
     * filter 를 filterChain 에  등록
     *
     * @param request     jwtToken 추출
     * @param response    filterChain 에 전송할 response
     * @param filterChain 토큰 유효성 검증하기 위한 filter 를 filter chain 에 등록
     */
    @Override
    protected void doFilterInternal(final @NotNull HttpServletRequest request,
                                    final @NotNull HttpServletResponse response,
                                    final @NotNull FilterChain filterChain) throws ServletException, IOException {
        final String token = this.jwtTokenProvider.resolveToken(request);

        try {

            if (token != null && this.jwtTokenProvider.validateToken(token)) {
                final Authentication auth = this.jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

            // EntryPoint 에서 에러 처리 해주기 위해 설정
        } catch (ExpiredTokenException e) {
            request.setAttribute(SecurityConstant.EXCEPTION, ErrorCode.EXPIRED_TOKEN.getCode());
            e.printStackTrace();
        } catch (Exception e) {
            request.setAttribute(SecurityConstant.EXCEPTION, ErrorCode.INVALID_TOKEN.getCode());
            e.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }

}
