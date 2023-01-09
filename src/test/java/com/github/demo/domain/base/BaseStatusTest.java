package com.github.demo.domain.base;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BaseStatusTest {

    @Test
    void isReady() {
        Assertions.assertTrue(BaseStatus.isReady(BaseStatus.READY));
        Assertions.assertFalse(BaseStatus.isReady(BaseStatus.ACTIVE));
        Assertions.assertFalse(BaseStatus.isReady(BaseStatus.DELETED));

    }

    @Test
    void isDeleted() {
        Assertions.assertFalse(BaseStatus.isDeleted(BaseStatus.READY));
        Assertions.assertFalse(BaseStatus.isDeleted(BaseStatus.ACTIVE));
        Assertions.assertTrue(BaseStatus.isDeleted(BaseStatus.DELETED));
    }
}
