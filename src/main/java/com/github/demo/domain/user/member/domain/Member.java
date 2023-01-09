package com.github.demo.domain.user.member.domain;

import com.github.demo.domain.user.common.domain.Account;
import com.github.demo.domain.user.common.domain.AccountType;
import com.github.demo.domain.user.common.dto.AccountDto;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@AllArgsConstructor
@SuperBuilder
public class Member extends Account implements Serializable {
    @Serial
    private static final long serialVersionUID = 32409238471221L;

    public static Member of(final AccountDto.SignUpRequest request) {
        return Member.builder()
                .email(request.email())
                .password(request.password())
                .build();
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.MEMBER;
    }
}
