package com.bucket.list.member.controller;

import com.bucket.list.auth.MemberDetails;
import com.bucket.list.auth.oauth2.user.provider.AuthProvider;
import com.bucket.list.dto.response.MessageResponseDto;
import com.bucket.list.dto.response.SingleResponseDto;
import com.bucket.list.dto.response.SingleResponseWithMessageDto;
import com.bucket.list.member.dto.MemberRequestDto;
import com.bucket.list.member.dto.MemberResponseDto;
import com.bucket.list.member.entity.Member;
import com.bucket.list.member.mapper.MemberMapper;
import com.bucket.list.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/v1/members")
@RequiredArgsConstructor
public class MemberController {
  private final MemberService memberService;
  private final MemberMapper mapper;

  @PostMapping("/validation")
  public ResponseEntity validationEmail(@Valid @RequestBody MemberRequestDto.EmailDto emailDto) {
    MemberResponseDto.EmailValidation emailValidation
      = mapper.validationEmailCheckToEmailValidation(memberService.validationEmailCheck(emailDto.getEmail()));
    return new ResponseEntity<>(new SingleResponseWithMessageDto<>(emailValidation, "SUCCESS"), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity getMember(@AuthenticationPrincipal MemberDetails memberDetails)  {
    Member member = memberService.findMember(memberDetails.getMemberId());

    MemberResponseDto.MemberInfo memberInfo = mapper.memberToMemberInfo(member);
    return new ResponseEntity<>(new SingleResponseDto(memberInfo), HttpStatus.OK);
  }

  @PatchMapping
  public ResponseEntity updateMember(@AuthenticationPrincipal MemberDetails memberDetails,
                                     @RequestBody MemberRequestDto.UpdateDto updateDto) {
    updateDto.setMemberId(memberDetails.getMemberId());
    Member member = memberService.updateMember(mapper.updateDtoToMember(updateDto));
    MemberResponseDto.UpdateDto memberInfo = mapper.memberToUpdateDto(member);
    return new ResponseEntity<>(new SingleResponseWithMessageDto(memberInfo, "SUCCESS"), HttpStatus.OK);
  }

  @DeleteMapping
  public ResponseEntity deleteMember(@AuthenticationPrincipal MemberDetails memberDetails) {
    memberService.deleteMember(memberDetails.getMemberId());

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
