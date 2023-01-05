package com.github.demo.domain.user.member.repository;

import com.github.demo.domain.base.Email;
import com.github.demo.domain.user.member.domain.Member;
import com.github.demo.domain.user.member.domain.QMember;
import com.github.demo.global.support.CustomQuerydslRepositorySupport;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.UUID;

@Repository
public class MemberRepository extends CustomQuerydslRepositorySupport {
    public MemberRepository() {
        super(Member.class);
    }

    private static final QMember MEMBER = QMember.member;

    @Transactional
    @SuppressWarnings("unchecked")
    public Member save(final Member member) {

        Assert.notNull(member, "Entity must not be null.");

        if (this.getEntityInformation().isNew(member)) {
            this.getEntityManager().persist(member);
            return member;
        } else {
            return this.getEntityManager().merge(member);
        }
    }

    @Transactional(readOnly = true)
    public Member findById(final UUID id) {
        return this.selectFrom(MEMBER)
                .where(
                        this.idEq(id)
                )
                .fetchOne();
    }

    @Transactional(readOnly = true)
    public Member findById(final String id) {
        return this.findById(UUID.fromString(id));
    }

    @Transactional(readOnly = true)
    public Member findByEmail(final Email email) {
        return this.selectFrom(MEMBER)
                .where(
                        this.emailEq(email)
                )
                .fetchOne();
    }

    private BooleanExpression idEq(final UUID id) {
        return id == null ? null : MEMBER.id.eq(id);
    }

    private BooleanExpression emailEq(final String email) {
        return email == null ? null : this.emailEq(Email.of(email));
    }

    private BooleanExpression emailEq(final Email email) {
        return email == null ? null : MEMBER.email.eq(email);
    }
}
