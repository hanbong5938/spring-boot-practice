package com.github.demo.global.security.config;

import com.github.demo.domain.user.common.domain.AccountType;
import com.github.demo.global.security.filter.JwtAuthenticationFilter;
import com.github.demo.global.security.handler.CustomAccessDeniedHandler;
import com.github.demo.global.security.handler.CustomAuthenticationEntryPoint;
import com.github.demo.global.security.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

    /**
     * rest api 이기에 httpBasic disable
     * csrf 보안 또한 rest api 이기에 불필요
     * jwt token 사용하여 인증하기 때문에 세션 사용X
     * request 권한 체크의 경우 해당 url 모두 허용
     * 이외의 경우 USER 권한 있는 경우에만 허용
     * jwt token 필터를 id/getPw 인증 필터 전에 삽입
     *
     * @param http security 설정
     */
    @Bean
    public SecurityFilterChain filterChain(final @NotNull HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .cors(Customizer.withDefaults())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests(authorize ->
                        authorize.requestMatchers(HttpMethod.OPTIONS, "/**/*").permitAll()
                                .requestMatchers(
                                        "/actuator/**", "/swagger-ui/**", "/api-docs/**",
                                        "/sign-in", "/sign-up").permitAll()
                                .requestMatchers("/*/member/**").hasAuthority(AccountType.MEMBER.name())
                                .requestMatchers("/*/admin/**").hasAuthority(AccountType.ADMIN.name())
                                .anyRequest().hasAnyAuthority(AccountType.MEMBER.name(), AccountType.ADMIN.name()))
                // 토큰 예외 발생시
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()
                // 권한 예외 발생시
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                .addFilterBefore(
                        new JwtAuthenticationFilter(this.jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
