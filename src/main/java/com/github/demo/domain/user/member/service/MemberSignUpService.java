package com.github.demo.domain.user.member.service;

import com.github.demo.domain.user.common.dto.AccountDto;
import com.github.demo.domain.user.common.service.SignUpService;
import com.github.demo.domain.user.member.domain.Member;
import com.github.demo.domain.user.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberSignUpService implements SignUpService {

    private final MemberRepository memberRepository;

    @Override
    public void signUp(final AccountDto.SignUpRequest request) {
        this.memberRepository.save((Member) request.toAccount());
    }
}
