package com.github.demo.domain.user.admin.service;

import com.github.demo.domain.user.admin.domain.Admin;
import com.github.demo.domain.user.admin.repository.AdminRepository;
import com.github.demo.domain.user.common.dto.AccountDto;
import com.github.demo.domain.user.common.service.SignUpService;
import com.github.demo.infra.mail.dto.MailDto;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminSignUpService implements SignUpService {

    private final AdminRepository adminRepository;
    private final ApplicationEventPublisher applicationEventPublisher;


    @Override
    @Transactional
    public void signUp(AccountDto.@NotNull SignUpRequest request) {
        SignUpService.existsEmail(this.adminRepository, request.email());
        final Admin admin = this.adminRepository.save((Admin) request.toAccount());
        this.applicationEventPublisher.publishEvent(MailDto.MailRequest.of(admin, "가입", "성공"));
    }
}
