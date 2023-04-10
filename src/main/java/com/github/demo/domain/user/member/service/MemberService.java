package com.github.demo.domain.user.member.service;

import com.github.demo.domain.user.member.domain.Member;
import com.github.demo.domain.user.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public List<Member> getAllMember() {
        return this.memberRepository.findAll();
    }
}
