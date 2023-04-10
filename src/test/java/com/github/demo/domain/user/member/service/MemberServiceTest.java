package com.github.demo.domain.user.member.service;

import com.github.demo.domain.user.member.domain.Member;
import com.github.demo.domain.user.member.domain.QMember;
import com.github.demo.global.support.SpringBootTestSupport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.List;

@Import(MemberService.class)
class MemberServiceTest extends SpringBootTestSupport {

    @Autowired
    private MemberService memberService;

    @Test
    void getAllMember() {
        // given
        var members = List.of(
                Member.builder().build(),
                Member.builder().build(),
                Member.builder().build()
        );
        members.forEach(this::save);

        //when
        var query = this.jpaQueryFactory.selectFrom(QMember.member).fetch();
        var result = this.memberService.getAllMember();

        //then
        Assertions.assertEquals(query.size(), result.size());
    }
}
