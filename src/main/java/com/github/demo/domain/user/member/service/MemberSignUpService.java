package com.github.demo.domain.user.member.service;

import com.github.demo.domain.user.common.dto.AccountDto;
import com.github.demo.domain.user.common.service.SignUpService;
import com.github.demo.domain.user.member.domain.Member;
import com.github.demo.domain.user.member.repository.MemberRepository;
import com.github.demo.infra.mail.dto.MailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberSignUpService implements SignUpService {

    private final MemberRepository memberRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void signUp(final AccountDto.SignUpRequest request) {
        final Member member = this.memberRepository.save((Member) request.toAccount());
        this.applicationEventPublisher.publishEvent(MailDto.MailRequest.of(member, "가입", "성공"));

    }
}
