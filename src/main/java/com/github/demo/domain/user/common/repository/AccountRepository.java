package com.github.demo.domain.user.common.repository;

import com.github.demo.domain.base.Email;
import com.github.demo.domain.user.common.domain.Account;
import com.github.demo.global.support.CustomQuerydslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.UUID;

public abstract class AccountRepository<T extends Account> extends CustomQuerydslRepositorySupport {

    protected AccountRepository(final Class<T> account) {
        super(account);
    }
    @Transactional
    @SuppressWarnings("unchecked")
    public T save(final T account) {

        Assert.notNull(account, "Entity must not be null.");

        if (this.getEntityInformation().isNew(account)) {
            this.getEntityManager().persist(account);
            return account;
        } else {
            return this.getEntityManager().merge(account);
        }
    }

    @Transactional(readOnly = true)
    public Account findById(final String id) {
        return this.findById(UUID.fromString(id));
    }

    @Transactional(readOnly = true)
    public Account findByEmail(final String email) {
        return this.findByEmail(Email.of(email));
    }

    public abstract Account findById(final UUID id);

    public abstract Account findByEmail(final Email email);

}
