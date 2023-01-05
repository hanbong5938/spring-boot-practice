package com.github.demo.domain.user.common.factory;

import com.github.demo.domain.user.admin.service.AdminDetailsService;
import com.github.demo.domain.user.common.domain.AccountType;
import com.github.demo.domain.user.member.service.MemberDetailService;
import com.github.demo.global.factory.AbstractAccountTypeFactory;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailsFactory extends AbstractAccountTypeFactory<UserDetailsService> {
    private final AdminDetailsService adminSignUpService;
    private final MemberDetailService memberDetailsService;

    public UserDetailsService getService(final @NotNull AccountType accountType) throws IllegalArgumentException {
        return switch (accountType) {
            case ADMIN -> this.adminSignUpService;
            case MEMBER -> this.memberDetailsService;
        };
    }
}
