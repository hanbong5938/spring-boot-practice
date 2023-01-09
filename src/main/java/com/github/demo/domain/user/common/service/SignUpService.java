package com.github.demo.domain.user.common.service;

import com.github.demo.domain.base.Email;
import com.github.demo.domain.user.common.dto.AccountDto;
import com.github.demo.domain.user.common.repository.AccountRepository;
import org.jetbrains.annotations.NotNull;

public interface SignUpService {
    void signUp(AccountDto.SignUpRequest request);

    static void existsEmail(final @NotNull AccountRepository<?> accountRepository, final Email email) {
        if (accountRepository.existByEmail(email)) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
    }
}
