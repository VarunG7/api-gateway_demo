package com.microserrvices.member.controller;

import com.microserrvices.member.model.Member;
import com.microserrvices.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public List<Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable String id) {
        return memberService.getMemberById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Member> registerMember(@RequestBody Member member) {
        return ResponseEntity.ok(memberService.registerMember(member));
    }

    @GetMapping("/{id}/validate")
    public ResponseEntity<Boolean> validateMembership(@PathVariable String id) {
        return ResponseEntity.ok(memberService.validateMembership(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Member> updateStatus(@PathVariable String id, @RequestParam boolean active) {
        return memberService.updateStatus(id, active)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
