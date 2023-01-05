package com.github.demo.domain.user.common.service;

import com.github.demo.domain.user.common.dto.AccountDto;

public interface SignUpService {
    void signUp(AccountDto.SignUpRequest request);
}
