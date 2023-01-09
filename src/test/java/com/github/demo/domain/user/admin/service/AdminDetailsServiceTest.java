package com.github.demo.domain.user.admin.service;

import com.github.demo.domain.base.Email;
import com.github.demo.domain.user.admin.domain.Admin;
import com.github.demo.domain.user.admin.repository.AdminRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class AdminDetailsServiceTest {

    @Mock
    AdminRepository adminRepository;

    @InjectMocks
    AdminDetailsService adminDetailsService;

    private Admin admin;

    @BeforeEach
    public void setup() {
        admin = Admin.builder()
                .id(UUID.fromString("81f14942-3e9b-4144-a1dc-b683fefacb8e"))
                .email(Email.of("test@test.com"))
                .build();
    }


    @ParameterizedTest
    @ValueSource(strings = {"81f14942-3e9b-4144-a1dc-b683fefacb8e"})
    void loadUserByUsername(String param) {
        // given
        BDDMockito.given(this.adminRepository.findById(param))
                .willReturn(admin);

        // when
        Admin userDetails = (Admin) this.adminDetailsService.loadUserByUsername(param);

        // then
        Assertions.assertEquals(admin.getId(), userDetails.getId());
    }
}
