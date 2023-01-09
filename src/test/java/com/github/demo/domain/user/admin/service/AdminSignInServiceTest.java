package com.github.demo.domain.user.admin.service;

import com.github.demo.domain.base.BaseStatus;
import com.github.demo.domain.base.Email;
import com.github.demo.domain.user.admin.domain.Admin;
import com.github.demo.domain.user.admin.repository.AdminRepository;
import com.github.demo.domain.user.common.domain.AccountType;
import com.github.demo.domain.user.common.domain.Password;
import com.github.demo.domain.user.common.dto.AccountDto;
import com.github.demo.global.security.provider.JwtTokenProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class AdminSignInServiceTest {
    @Mock
    AdminRepository adminRepository;

    @Mock
    JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    AdminSignInService adminSignInService;

    private Admin admin;

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
    void signIn() {
        //given
        var request = new AccountDto.SignInRequest(AccountType.ADMIN,
                Email.of("test@test.com"),
                new Password("1234"));
        BDDMockito.given(this.adminRepository.findByEmail(request.email()))
                .willReturn(admin);

        //when
        var data = this.adminSignInService.signIn(request);

        //then
        Assertions.assertNotNull(data);
    }
}
