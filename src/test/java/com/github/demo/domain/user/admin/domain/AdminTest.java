package com.github.demo.domain.user.admin.domain;

import com.github.demo.domain.base.Email;
import com.github.demo.domain.user.common.domain.AccountType;
import com.github.demo.domain.user.common.domain.Password;
import com.github.demo.domain.user.common.dto.AccountDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AdminTest {

    @Test
    void getAccountType() {
        var admin = Admin.of(new AccountDto.SignUpRequest(
                AccountType.ADMIN, Email.of("test@test.com"), Password.of("1234")));
        Assertions.assertEquals(AccountType.ADMIN, admin.getAccountType());
    }

    @Test
    void of() {
        var admin = Admin.of(new AccountDto.SignUpRequest(
                AccountType.ADMIN, Email.of("test@test.com"), Password.of("1234")));
        Assertions.assertEquals(AccountType.ADMIN, admin.getAccountType());
        Assertions.assertEquals("test@test.com", admin.getEmail().getValue());
        Assertions.assertNotEquals("1234", admin.getPassword());
    }

}
