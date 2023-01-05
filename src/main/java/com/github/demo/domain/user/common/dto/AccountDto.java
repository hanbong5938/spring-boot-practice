package com.github.demo.domain.user.common.dto;

import com.github.demo.domain.base.Email;
import com.github.demo.domain.user.admin.domain.Admin;
import com.github.demo.domain.user.common.domain.Account;
import com.github.demo.domain.user.common.domain.AccountType;
import com.github.demo.domain.user.common.domain.Password;
import com.github.demo.domain.user.member.domain.Member;
import lombok.Builder;

public record AccountDto() {

    public record SignInRequest(AccountType accountType, Email email, Password password) {

    }

    public record SignUpRequest(AccountType accountType, Email email, Password password) {
        public Account toAccount() {
            return switch (this.accountType) {
                case ADMIN -> Admin.of(this);
                case MEMBER -> Member.of(this);
            };
        }
    }

    @Builder
    public record TokenResponse(String accessToken, String refreshToken) {
        public static TokenResponse of(final String accessToken, final String refreshToken) {
            return TokenResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        }
    }
}
