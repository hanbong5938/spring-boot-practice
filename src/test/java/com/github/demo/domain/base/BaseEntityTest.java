package com.github.demo.domain.base;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BaseEntityTest {

    @Test
    void deleted() {
        var baseEntity = new BaseEntityTesAble();
        Assertions.assertFalse(baseEntity.getDeleted());
        baseEntity.deleted();
        Assertions.assertTrue(baseEntity.getDeleted());
    }

    @Test
    void getId() {
        var baseEntity = new BaseEntityTesAble();
        Assertions.assertNotNull(baseEntity.getId());
    }

    @Test
    void getDeleted() {
        var baseEntity = new BaseEntityTesAble();
        Assertions.assertFalse(baseEntity.getDeleted());
        baseEntity.deleted();
        Assertions.assertTrue(baseEntity.getDeleted());
    }

    private static class BaseEntityTesAble extends BaseEntity {
    }
}
