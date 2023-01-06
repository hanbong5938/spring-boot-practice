package com.github.demo.domain.user.admin.repository;

import com.github.demo.domain.base.Email;
import com.github.demo.domain.user.admin.domain.Admin;
import com.github.demo.domain.user.admin.domain.QAdmin;
import com.github.demo.domain.user.common.repository.AccountRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public class AdminRepository extends AccountRepository<Admin> {
    public AdminRepository() {
        super(Admin.class);
    }

    private static final QAdmin ADMIN = QAdmin.admin;

    @Transactional(readOnly = true)
    public Admin findById(final UUID id) {
        return this.selectFrom(ADMIN)
                .where(
                        this.idEq(id)
                )
                .fetchOne();
    }

    @Transactional(readOnly = true)
    public Admin findByEmail(final Email email) {
        return this.selectFrom(ADMIN)
                .where(
                        this.emailEq(email)
                )
                .fetchOne();
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existByEmail(final Email email) {
        UUID one = this.select(ADMIN.id)
                .from(ADMIN)
                .where(
                        this.emailEq(email)
                ).fetchOne();
        return one != null;
    }

    private BooleanExpression idEq(final UUID id) {
        return id == null ? null : ADMIN.id.eq(id);
    }

    private BooleanExpression emailEq(final Email email) {
        return email == null ? null : ADMIN.email.eq(email);
    }

}
