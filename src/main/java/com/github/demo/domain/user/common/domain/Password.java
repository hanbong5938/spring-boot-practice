package com.github.demo.domain.user.common.domain;

import com.github.demo.constant.DateTimeFormatConstant;
import com.github.demo.constant.SecurityConstant;
import com.github.demo.global.error.exception.PasswordNotMatchedException;
import com.github.demo.global.error.exception.UserNotAllowException;
import com.github.demo.global.error.exception.UserNotFoundException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@ToString
public class Password implements Serializable {

    @Serial
    private static final long serialVersionUID = 3903415264190251207L;

    @Transient
    private static PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Length
    @Column(name = "password", length = 100)
    private String value;

    /**
     * 최근 비밀 번호 변경 일자
     */
    @Column(columnDefinition = "datetime default now()")
    @DateTimeFormat(pattern = DateTimeFormatConstant.DATE_TIME_FORMAT)
    private LocalDateTime lastUpdatePassword;

    /**
     * 로그인 실패
     * default 0
     *
     */
    @Column(columnDefinition = "int(1) default 0")
    @NotEmpty
    private Integer failedCount;

    /**
     * 비밀 번호 변경 인증 번호 발급 일자
     */
    private LocalDateTime issuedPasswordAuthDate;

    /**
     * 비밀번호 암호화
     *
     * @param password 비밀번호
     * @return 암호화된 비밀번호
     */
    public static Password of(final String password) {
        return Password.builder()
                .value(encoder.encode(password))
                .lastUpdatePassword(updateDate())
                .failedCount(0)
                .build();
    }

    /**
     * 비밀번호 암호화
     *
     * @return 암호화된 비밀번호
     */
    public static Password of() {
        return Password.builder()
                .value(null)
                .lastUpdatePassword(updateDate())
                .failedCount(0)
                .build();
    }

    public static Password encodeOf(final String encoderPassword) {
        return Password.builder()
                .value(encoderPassword)
                .lastUpdatePassword(updateDate())
                .failedCount(0)
                .build();
    }

    @Contract(" -> new")
    private static @NotNull LocalDateTime updateDate() {
        return LocalDateTime.now();
    }

    public boolean isMatched(final String rawPassword) {
        if (failedCount >= SecurityConstant.SIGN_IN_FAIL_LIMIT) {
            throw new UserNotFoundException();
        }

        final boolean matches = this.isMatches(rawPassword);
        updateFailedCount(matches);
        return matches;
    }

    public Password changePassword(final String newPassword, final String oldPassword) {
        if (!this.isMatches(oldPassword)) {
            throw new PasswordNotMatchedException();
        }
        this.value = this.encodePassword(newPassword);
        this.lastUpdatePassword = updateDate();
        this.failedCount = 0;
        this.issuedPasswordAuthDate = null;
        return this;
    }

    public void isExpiredCode() {
        final LocalDateTime issuedDate = this.getIssuedPasswordAuthDate();
        if (issuedDate == null || issuedDate.plusMinutes(3).isAfter(LocalDateTime.now())) {
            throw new UserNotAllowException();
        }
    }

    public boolean isExpiration() {
        if (this.getIssuedPasswordAuthDate() == null) {
            return true;
        }
        return LocalDateTime.now().minusDays(SecurityConstant.EXPIRED_PASSWORD).isAfter(lastUpdatePassword);
    }

    private String encodePassword(final String password) {
        return encoder.encode(password);
    }

    private void updateFailedCount(boolean matches) {
        if (matches)
            this.resetFailedCount();
        else
            this.increaseFailCount();
    }

    private void resetFailedCount() {
        this.failedCount = 0;
    }

    private void increaseFailCount() {
        this.failedCount++;
    }

    private boolean isMatches(final String rawPassword) {
        return encoder.matches(rawPassword, this.value);
    }

    public String getPassword() {
        return "{bcrypt}" + value;
    }

    public String getOriginalValue() {
        return value;
    }

    public static PasswordEncoder getEncoder() {
        return encoder;
    }

    public Password(final String value) {
        this.value = value;
    }

}
