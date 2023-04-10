package com.github.demo.domain.user.common.api;

import com.github.demo.domain.base.BaseStatus;
import com.github.demo.domain.base.Email;
import com.github.demo.domain.user.admin.domain.Admin;
import com.github.demo.domain.user.admin.service.AdminSignInService;
import com.github.demo.domain.user.common.domain.AccountType;
import com.github.demo.domain.user.common.domain.Password;
import com.github.demo.domain.user.common.dto.AccountDto;
import com.github.demo.domain.user.common.factory.SignInFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

@ExtendWith(SpringExtension.class)
@WebMvcTest
class SignInApiTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AdminSignInService adminSignInService;

    @MockBean
    SignInFactory signInFactory;

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
    void signIn() {
        // given
        var request = new AccountDto.SignInRequest(AccountType.ADMIN, Email.of("test@test.com"), Password.of("1234"));
        BDDMockito.given(this.signInFactory.getService(AccountType.ADMIN).signIn(request))
                .willReturn(AccountDto.TokenResponse.builder().accessToken("").refreshToken("").build());

        // when
    }
}
