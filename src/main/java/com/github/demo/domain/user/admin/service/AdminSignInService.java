package com.github.demo.domain.user.admin.service;

import com.github.demo.domain.user.admin.repository.AdminRepository;
import com.github.demo.domain.user.common.domain.Account;
import com.github.demo.domain.user.common.dto.AccountDto;
import com.github.demo.domain.user.common.service.SignInService;
import com.github.demo.global.security.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminSignInService implements SignInService {

    private final AdminRepository adminRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional(readOnly = true)
    public AccountDto.TokenResponse signIn(final AccountDto.@NotNull SignInRequest request) {
        final Account account = this.adminRepository.findByEmail(request.email());
        return SignInService.getTokenRes(this.jwtTokenProvider, account, request.password());
    }
}
