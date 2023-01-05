package com.github.demo.domain.user.member.service;

import com.github.demo.domain.user.common.domain.Account;
import com.github.demo.domain.user.common.dto.AccountDto;
import com.github.demo.domain.user.common.service.SignInService;
import com.github.demo.domain.user.member.repository.MemberRepository;
import com.github.demo.global.security.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberSignInService implements SignInService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional(readOnly = true)
    public AccountDto.TokenResponse signIn(final AccountDto.@NotNull SignInRequest req) {
        final Account account = this.memberRepository.findByEmail(req.email());
        return SignInService.getTokenRes(this.jwtTokenProvider, account, req.password());
    }
}
