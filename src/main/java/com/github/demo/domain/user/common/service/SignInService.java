package com.github.demo.domain.user.common.service;

import com.github.demo.domain.user.common.domain.Account;
import com.github.demo.domain.user.common.domain.Password;
import com.github.demo.domain.user.common.dto.AccountDto;
import com.github.demo.global.error.exception.PasswordNotMatchedException;
import com.github.demo.global.error.exception.UserNotAllowException;
import com.github.demo.global.security.provider.JwtTokenProvider;
import org.jetbrains.annotations.NotNull;

public interface SignInService {
    AccountDto.TokenResponse signIn(AccountDto.SignInRequest req);

    static AccountDto.TokenResponse getTokenRes(final JwtTokenProvider jwtTokenProvider,
                                                final @NotNull Account account,
                                                final Password password) {
        if (!account.isEnabled()) {
            throw new UserNotAllowException();
        }

        if (!account.getPasswordObject().isMatched(password.getValue())) {
            throw new PasswordNotMatchedException();
        }

        return AccountDto.TokenResponse.of(
                jwtTokenProvider.generateToken(account),
                jwtTokenProvider.generateToken(account));
    }
}
