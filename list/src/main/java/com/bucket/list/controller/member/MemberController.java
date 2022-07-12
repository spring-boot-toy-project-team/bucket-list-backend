package com.bucket.list.controller.member;


import com.bucket.list.dto.member.MemberRequestDto;
import com.bucket.list.entity.member.Member;
import com.bucket.list.mapper.member.MemberMapper;
import com.bucket.list.service.member.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/v1/members")
public class MemberController {

    private final MemberService memberService;
    private final MemberMapper mapper;

    public MemberController(MemberService memberService, MemberMapper mapper) {
        this.memberService = memberService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity postMember(@RequestBody MemberRequestDto.memberSignUpDto memberSignUpDto){
        Member member = mapper.memberPostToMember(memberSignUpDto);
        Member createdMember = memberService.createMember(member);


        return new ResponseEntity<>(mapper.memberToMemberSignUp(createdMember), HttpStatus.CREATED);
    }

    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(@PathVariable("member-id") @Positive long memberId, @RequestBody MemberRequestDto.updateMember updateMemberDto){
        updateMemberDto.setMemberId(memberId);

        Member updateMember = memberService.updateMember(mapper.memberPostToMember(updateMemberDto));

        return new ResponseEntity(mapper.memberToMemberModify(updateMember), HttpStatus.OK);
    }

    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@PathVariable("member-id") @Positive long memberId){
        Member member = memberService.findMembers(memberId);
        return new ResponseEntity<>(mapper.memberToMemberFindMember(member), HttpStatus.OK);
    }

    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@PathVariable("member-id") @Positive long memberId){
        memberService.deleteMember(memberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
