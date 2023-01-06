package com.github.demo.domain.user.admin.service;

import com.github.demo.domain.user.admin.domain.Admin;
import com.github.demo.domain.user.admin.repository.AdminRepository;
import com.github.demo.domain.user.common.dto.AccountDto;
import com.github.demo.domain.user.common.service.SignUpService;
import com.github.demo.infra.mail.dto.MailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminSignUpService implements SignUpService {

    private final AdminRepository adminRepository;
    private final ApplicationEventPublisher applicationEventPublisher;


    @Override
    public void signUp(AccountDto.SignUpRequest request) {
        final Admin admin = this.adminRepository.save((Admin) request.toAccount());
        this.applicationEventPublisher.publishEvent(MailDto.MailRequest.of(admin, "가입", "성공"));
    }
}
