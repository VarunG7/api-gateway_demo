package com.microserrvices.member.service;

import com.microserrvices.member.model.Member;
import com.microserrvices.member.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @PostConstruct
    public void initDefaultData() {
        if (memberRepository.count() == 0) {
            Member m1 = new Member(UUID.randomUUID().toString(), "Alice Smith", "alice@example.com", "PREMIUM", true);
            Member m2 = new Member(UUID.randomUUID().toString(), "Bob Jones", "bob@example.com", "STANDARD", true);
            memberRepository.saveAll(List.of(m1, m2));
        }
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> getMemberById(String id) {
        return memberRepository.findById(id);
    }

    @Transactional
    public Member registerMember(Member member) {
        if (member.getId() == null || member.getId().isBlank()) {
            member.setId(UUID.randomUUID().toString());
        }
        member.setActive(true);
        if (member.getMembershipType() == null || member.getMembershipType().isBlank()) {
            member.setMembershipType("STANDARD");
        }
        return memberRepository.save(member);
    }

    public boolean validateMembership(String memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        return member.isPresent() && member.get().isActive();
    }

    @Transactional
    public Optional<Member> updateStatus(String memberId, boolean active) {
        Optional<Member> memberOpt = memberRepository.findById(memberId);
        if (memberOpt.isPresent()) {
            Member member = memberOpt.get();
            member.setActive(active);
            return Optional.of(memberRepository.save(member));
        }
        return Optional.empty();
    }
}
