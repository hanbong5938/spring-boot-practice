package com.github.demo.domain.user.member.repository;

import com.github.demo.domain.base.Email;
import com.github.demo.domain.user.common.repository.AccountRepository;
import com.github.demo.domain.user.member.domain.Member;
import com.github.demo.domain.user.member.domain.QMember;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public class MemberRepository extends AccountRepository<Member> {
    public MemberRepository() {
        super(Member.class);
    }

    private static final QMember MEMBER = QMember.member;

    @Transactional(readOnly = true)
    public Member findById(final UUID id) {
        return this.selectFrom(MEMBER)
                .where(
                        this.idEq(id)
                )
                .fetchOne();
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

    private BooleanExpression emailEq(final Email email) {
        return email == null ? null : MEMBER.email.eq(email);
    }
}
