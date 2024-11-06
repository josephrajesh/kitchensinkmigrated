package com.mongodb.incubator.challenge.kitchensinkmigrated.service;

import com.mongodb.incubator.challenge.kitchensinkmigrated.Repository.MemberRepository;
import com.mongodb.incubator.challenge.kitchensinkmigrated.model.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Slf4j
public class MemberRegistration {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    public MemberRegistration(MemberRepository memberRepository, MemberService memberService) {
        this.memberRepository = memberRepository;
        this.memberService = memberService;
    }

    @Transactional
    public List<Member> register(Member member) throws Exception {
        log.info("Saving {} ", member);
        if (member.id() == null) {
            member = member.withId(memberRepository.findFirstByOrderByIdDesc(0L).map(m -> m.id() + 1).orElse(1L)); // Generate a new ID if not provided (for new members
        }
        Member savedMember = memberRepository.save(member);
        log.info("Saved {}", savedMember);
        return memberService.getAllMembers();
    }
}

