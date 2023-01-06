package com.github.demo.domain.user.member.service;

import com.github.demo.domain.user.common.dto.AccountDto;
import com.github.demo.domain.user.common.service.SignUpService;
import com.github.demo.domain.user.member.domain.Member;
import com.github.demo.domain.user.member.repository.MemberRepository;
import com.github.demo.infra.mail.dto.MailDto;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberSignUpService implements SignUpService {

    private final MemberRepository memberRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional
    public void signUp(final AccountDto.@NotNull SignUpRequest request) {
        SignUpService.existsEmail(this.memberRepository, request.email());
        final Member member = this.memberRepository.save((Member) request.toAccount());
        this.applicationEventPublisher.publishEvent(MailDto.MailRequest.of(member, "가입", "성공"));
    }
}
