package com.github.demo.domain.base;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public enum BaseStatus {
    ACTIVE,
    READY,
    DELETED;

    @Contract(pure = true)
    public static boolean isReady(final @NotNull BaseStatus status) {
        return status.isReady();
    }

    private boolean isReady() {
        return this == READY;
    }

    @Contract(pure = true)
    public static boolean isDeleted(final @NotNull BaseStatus status) {
        return status.isDeleted();
    }

    private boolean isDeleted() {
        return this == DELETED;
    }
}
