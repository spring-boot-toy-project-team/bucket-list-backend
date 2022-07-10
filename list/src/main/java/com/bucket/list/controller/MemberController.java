package com.bucket.list.controller;


import com.bucket.list.entity.member.Member;
import com.bucket.list.requestDto.member.MemberPatchDto;
import com.bucket.list.requestDto.member.MemberPostDto;
import com.bucket.list.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/v1/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity postMember(@RequestBody MemberPostDto memberPostDto){
        Member member = memberService.createdMember(memberPostDto);

        return new ResponseEntity<>(member, HttpStatus.CREATED);
    }

    @PatchMapping(value = "/{member-id}")
    public ResponseEntity patchMember(@PathVariable("member-id") @Positive long memberId, @RequestBody MemberPatchDto memberPatchDto){
        memberPatchDto.setMemberId(memberId);

        Member member = memberService.updateMember()
    }
}
