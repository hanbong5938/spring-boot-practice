package com.github.demo.domain.base;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @ParameterizedTest
    @ValueSource(strings = {"test@test-mail.com", "test2@test-mail.com"})
    void of(String param) {
        var email = Email.of(param);
        Assertions.assertEquals(param, email.getValue());
    }

    @ParameterizedTest
    @ValueSource(strings = {"test@test-mail.com", "test2@test-mail.com"})
    void getHost(String param) {
        var email = Email.of(param);
        Assertions.assertEquals("test-mail.com", email.getHost());
    }

    @ParameterizedTest
    @ValueSource(strings = {"test@test-mail.com"})
    void getId(String param) {
        var email = Email.of(param);
        Assertions.assertEquals("test", email.getId());
    }
}
