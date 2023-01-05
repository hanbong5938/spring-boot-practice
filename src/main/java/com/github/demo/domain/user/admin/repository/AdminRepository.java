package com.github.demo.domain.user.admin.repository;

import com.github.demo.domain.base.Email;
import com.github.demo.domain.user.admin.domain.Admin;
import com.github.demo.domain.user.admin.domain.QAdmin;
import com.github.demo.global.support.CustomQuerydslRepositorySupport;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.UUID;

@Repository
public class AdminRepository extends CustomQuerydslRepositorySupport {
    public AdminRepository() {
        super(Admin.class);
    }

    private static final QAdmin ADMIN = QAdmin.admin;

    @Transactional
    @SuppressWarnings("unchecked")
    public Admin save(final Admin admin) {

        Assert.notNull(admin, "Entity must not be null.");

        if (this.getEntityInformation().isNew(admin)) {
            this.getEntityManager().persist(admin);
            return admin;
        } else {
            return this.getEntityManager().merge(admin);
        }
    }

    @Transactional(readOnly = true)
    public Admin findById(final UUID id) {
        return this.selectFrom(ADMIN)
                .where(
                        this.idEq(id)
                )
                .fetchOne();
    }

    @Transactional(readOnly = true)
    public Admin findById(final String id) {
        return this.findById(UUID.fromString(id));
    }

    @Transactional(readOnly = true)
    public Admin findByEmail(final String email) {
        return this.findByEmail(Email.of(email));
    }

    @Transactional(readOnly = true)
    public Admin findByEmail(final Email email) {
        return this.selectFrom(ADMIN)
                .where(
                        this.emailEq(email)
                )
                .fetchOne();
    }

    private BooleanExpression idEq(final UUID id) {
        return id == null ? null : ADMIN.id.eq(id);
    }

    private BooleanExpression emailEq(final Email email) {
        return email == null ? null : ADMIN.email.eq(email);
    }

}
