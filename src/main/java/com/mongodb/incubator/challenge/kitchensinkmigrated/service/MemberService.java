package com.mongodb.incubator.challenge.kitchensinkmigrated.service;

import com.mongodb.incubator.challenge.kitchensinkmigrated.Repository.MemberRepository;
import com.mongodb.incubator.challenge.kitchensinkmigrated.model.Member;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public List<Member> findMembersByName(String name) {
        return memberRepository.findByName(name);
    }

    public Optional<Member> findMemberById(Long Id) {
        return memberRepository.findById(Id);
    }
}