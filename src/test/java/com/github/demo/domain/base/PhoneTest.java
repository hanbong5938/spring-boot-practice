package com.github.demo.domain.base;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PhoneTest {

    @Test
    void of() {
        var phone = Phone.builder().value("01012341234").build();
        Assertions.assertNotNull(phone);
    }

    @ParameterizedTest
    @ValueSource(strings = {"01012341234", "01043211234", "1234"})
    void getLastPhoneNumber(String param) {
        var phone = Phone.builder().value(param).build();
        Assertions.assertEquals(1234, phone.getLastPhoneNumber());

    }

    @ParameterizedTest
    @ValueSource(strings = {"01012340234", "01043210234", "0234"})
    void getCode(String param) {
        var phone = Phone.builder().value(param).build();
        Assertions.assertEquals("0234", phone.getCode());
    }

    @Test
    void getValue() {
        var phone = Phone.builder().value("01012341234").build();
        Assertions.assertEquals("01012341234", phone.getValue());
    }
}
