package com.github.demo.global.factory;

import com.github.demo.domain.user.common.domain.AccountType;

public abstract class AbstractAccountTypeFactory<S> {
    protected abstract S getService(final AccountType accountType);
}
