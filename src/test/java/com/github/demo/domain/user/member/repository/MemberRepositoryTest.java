package com.github.demo.domain.user.member.repository;

import com.github.demo.domain.user.member.domain.Member;
import com.github.demo.global.annotation.RepositoryTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;


@Import(MemberRepository.class)
@RepositoryTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void findById() {
        //given
        var member = Member.builder().build();
        var save = this.memberRepository.save(member);

        // when
        var one = this.memberRepository.findById(save.getId());

        //then
        Assertions.assertEquals(save.getId(), one.getId());
    }

}
