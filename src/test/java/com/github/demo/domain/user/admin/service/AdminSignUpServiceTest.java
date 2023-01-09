package com.github.demo.domain.user.admin.service;

import com.github.demo.domain.base.BaseStatus;
import com.github.demo.domain.base.Email;
import com.github.demo.domain.user.admin.domain.Admin;
import com.github.demo.domain.user.admin.repository.AdminRepository;
import com.github.demo.domain.user.common.domain.AccountType;
import com.github.demo.domain.user.common.domain.Password;
import com.github.demo.domain.user.common.dto.AccountDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class AdminSignUpServiceTest {


    @Mock
    AdminRepository adminRepository;

    @Mock
    ApplicationEventPublisher applicationEventPublisher;

    @InjectMocks
    AdminSignUpService adminSignUpService;

    Admin admin;

    @BeforeEach
    public void setup() {
        admin = Admin.builder()
                .id(UUID.fromString("81f14942-3e9b-4144-a1dc-b683fefacb8e"))
                .email(Email.of("test@test.com"))
                .status(BaseStatus.ACTIVE)
                .password(Password.of("1234"))
                .build();
    }

    @Test
    void signUp() {
        //given
        var request = new AccountDto.SignUpRequest(AccountType.ADMIN,
                Email.of("test@test.com"), Password.of("1234"));
        BDDMockito.given(this.adminRepository.existByEmail(request.email())).willReturn(false);

        //when
        this.adminSignUpService.signUp(request);

        //then
        Assertions.assertTrue(true);
    }
}
