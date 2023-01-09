package com.github.demo.domain.base;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DateTimeTest {

    @Test
    void getCreatedAt() {
        var dateTime = new DateTimeTestAble();
        Assertions.assertNull(dateTime.getCreatedAt());
    }

    @Test
    void getUpdatedAt() {
        var dateTime = new DateTimeTestAble();
        Assertions.assertNull(dateTime.getUpdatedAt());
    }

    private static class DateTimeTestAble extends DateTime {
    }
}
