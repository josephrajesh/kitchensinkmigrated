package com.mongodb.incubator.challenge.kitchensinkmigrated.service;

import com.mongodb.incubator.challenge.kitchensinkmigrated.Repository.MemberRepository;
import com.mongodb.incubator.challenge.kitchensinkmigrated.model.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import java.util.List;
import java.util.Optional;


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
//        if (memberRepository.findByEmail(member.email()).isPresent()) {
//            throw new RuntimeException("Member with email " + member.email() + " already exists");
//        }
        if (member.id() == null) {
            member = member.withId(memberRepository.findFirstByIdGreaterThan(0L, Sort.by(Sort.Direction.DESC, "_id")).map(m -> m.id() + 1).orElse(1L)); // Generate a new ID if not provided (for new members
        }
//        if (memberRepository.findById(member.id()).isPresent()) {
//            throw new RuntimeException("Member with name " + member.id() + " already exists");
//        }
        Member savedMember = memberRepository.save(member);
        log.info("Saved {}", savedMember);
        return memberService.getAllMembers();
    }
}

