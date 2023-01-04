package com.github.demo.domain.user.member.repository;

import com.github.demo.domain.user.member.domain.Member;
import com.github.demo.global.support.CustomQuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository extends CustomQuerydslRepositorySupport {
    public MemberRepository() {
        super(Member.class);
    }

}
