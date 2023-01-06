package com.github.demo.global.validation.common;

import org.jetbrains.annotations.NotNull;

public class CommonValidator {
    public boolean isaBoolean(final boolean @NotNull ... dto) {
        for (boolean b : dto) {
            if (!b) {
                return false;
            }
        }
        return true;
    }



    public boolean isNull(final Object o) {
        return o == null;
    }

    public boolean nonNull(final Object o) {
        return o != null;
    }

    public boolean isEmpty(final String s) {
        return this.nonNull(s) || s.isEmpty();
    }

    public boolean nonEmpty(final String s)
    {
        return this.nonNull(s) && !s.isEmpty();
    }

}
