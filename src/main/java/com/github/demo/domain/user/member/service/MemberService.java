package com.github.demo.domain.user.member.service;

import com.github.demo.domain.user.member.domain.Member;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    public List<Member> getAllMember() {
        return List.of(Member.builder().build());
    }
}
