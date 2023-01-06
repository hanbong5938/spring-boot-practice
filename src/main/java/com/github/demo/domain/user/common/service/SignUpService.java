package com.github.demo.domain.user.common.service;

import com.github.demo.domain.base.Email;
import com.github.demo.domain.user.common.dto.AccountDto;
import com.github.demo.domain.user.common.repository.AccountRepository;

public interface SignUpService {
    void signUp(AccountDto.SignUpRequest request);

    static void existsEmail(AccountRepository<?> accountRepository, Email email) {
        if (accountRepository.existByEmail(email)) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
    }
}
