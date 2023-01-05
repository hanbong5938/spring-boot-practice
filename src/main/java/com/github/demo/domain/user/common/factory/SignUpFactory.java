package com.github.demo.domain.user.common.factory;

import com.github.demo.global.factory.AbstractAccountTypeFactory;
import com.github.demo.domain.user.admin.service.AdminSignUpService;
import com.github.demo.domain.user.common.domain.AccountType;
import com.github.demo.domain.user.common.service.SignUpService;
import com.github.demo.domain.user.member.service.MemberSignUpService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignUpFactory extends AbstractAccountTypeFactory<SignUpService> {

    private final AdminSignUpService  adminSignUpService;
    private final MemberSignUpService memberSignUpService;

    public SignUpService getService(final @NotNull AccountType accountType) {
        return switch (accountType) {
            case ADMIN -> this.adminSignUpService;
            case MEMBER -> this.memberSignUpService;
        };
    }
}
