package com.github.demo.domain.user.common.factory;

import com.github.demo.global.factory.AbstractAccountTypeFactory;
import com.github.demo.domain.user.admin.service.AdminSignInService;
import com.github.demo.domain.user.common.domain.AccountType;
import com.github.demo.domain.user.common.service.SignInService;
import com.github.demo.domain.user.member.service.MemberSignInService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignInFactory extends AbstractAccountTypeFactory<SignInService> {

    private final AdminSignInService adminSignInService;
    private final MemberSignInService memberSignInService;

    public SignInService getService(final @NotNull AccountType accountType) {
        return switch (accountType) {
            case ADMIN -> this.adminSignInService;
            case MEMBER -> this.memberSignInService;
        };
    }

}
