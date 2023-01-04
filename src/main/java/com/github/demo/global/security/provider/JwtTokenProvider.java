package com.github.demo.global.security.provider;

import com.github.demo.constant.SecurityConstant;
import com.github.demo.domain.user.common.domain.Account;
import com.github.demo.domain.user.common.domain.AccountType;
import com.github.demo.domain.user.common.factory.UserDetailsFactory;
import com.github.demo.global.error.exception.UserNotAllowException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final UserDetailsFactory userDetailsFactory;
    private final Key key = Keys.hmacShaKeyFor("#nef23bhySAD#783buy2)E#@J#R5httpFNc3vnJF%WEho;lkj;kl3^2@^#23@^#746uy^54%7t5".getBytes());

    public Claims getAllClaimsFromToken(final String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String getUsernameFromToken(final String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    /**
     * jwt 토큰 사용하여 인증 정보 조회
     *
     * @param token 회원정보 추출하기 위한 토큰
     */
    public Authentication getAuthentication(final String token) {
        final Claims claims = this.getAllClaimsFromToken(token);
        final UserDetails userDetails = this.userDetailsFactory.getService(this.getAccountType(claims))
                .loadUserByUsername(this.getId(claims));
        assert userDetails != null;

        // 잠겨있거나 탈퇴한 타입인 경우 에러 처리
        if (!userDetails.isAccountNonLocked() || !userDetails.isAccountNonExpired()) {
            throw new UserNotAllowException(userDetails.getUsername());
        }

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private @NotNull UUID toUUID(final String id) {
        return UUID.fromString(id);
    }

    private @NotNull String getId(final @NotNull Claims claims) {
        return claims.get(SecurityConstant.UUID).toString();
    }

    public UUID getId(final String token) {
        return UUID.fromString(this.getId(this.getAllClaimsFromToken(token)));
    }

    public UUID getId(final HttpServletRequest request) {
        final String token = this.resolveToken(request);
        return token == null ? null : UUID.fromString(this.getId(this.getAllClaimsFromToken(token)));
    }


    public AccountType getAccountType(final HttpServletRequest request) {
        final String token = this.resolveToken(request);
        return token == null ? null : this.getAccountType(this.getAllClaimsFromToken(token));
    }

    public AccountType getAccountType(final String token) {
        return token == null ? null : this.getAccountType(this.getAllClaimsFromToken(token));
    }

    private AccountType getAccountType(final @NotNull Claims claims) {
        return AccountType.valueOf(claims.get(SecurityConstant.JWT_CLAIMS).toString());
    }

    public Date getExpirationDateFromToken(final String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    private @NotNull Boolean isTokenExpired(final String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private String generateToken(final @NotNull Account account, final boolean isRefreshToken) {
        final Map<String, Object> claims = new HashMap<>();
        claims.put(SecurityConstant.JWT_CLAIMS, account.getAccountType());
        claims.put(SecurityConstant.UUID, account.getId());

        return this.doGenerateToken(claims, account.getUsername(), isRefreshToken);
    }

    private String doGenerateToken(final Map<String, Object> claims, final String username,
                                   final boolean isRefreshToken) {
        final Date createdDate = new Date();
        final Date expirationDate = new Date(isRefreshToken ?
                SecurityConstant.REFRESH_JWT_EXPIRED + createdDate.getTime()
                : SecurityConstant.ACCESS_JWT_EXPIRED + createdDate.getTime());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();
    }

    public boolean validateToken(final String token) {
        return !isTokenExpired(token);
    }

    /**
     * Request Header 에서 token 파싱
     *
     * @param request http 헤더 AUTHORIZE : jwt 토큰
     */
    public String resolveToken(final @NotNull HttpServletRequest request) {
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        return authorization != null ? authorization.substring(SecurityConstant.DEFAULT_TOKEN.length()) : null;
    }
}
