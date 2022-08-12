package com.bucket.list.stub.member;

import com.bucket.list.dto.response.MessageResponseDto;
import com.bucket.list.dto.token.TokenResponseDto;
import com.bucket.list.member.dto.MemberRequestDto;
import com.bucket.list.member.dto.MemberResponseDto;
import com.bucket.list.member.entity.Member;
import com.bucket.list.member.mapper.MemberMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MemberStub {

  public static Member getMember() {
    Member member = new Member();
    member.setMemberId(1L);
    member.setEmail("hgd@gmail.com");
    member.setTel("010-1234-5678");
    member.setIntroduction("hi");
    member.setNickName("hgd");
    member.setRoles("ROLE_USER");
    return member;
  }

  // 회원가입
  public static MemberRequestDto.SignUpDto signUpMemberDto() {
    return MemberRequestDto.SignUpDto.builder()
      .email("hgd@gmail.com")
      .nickName("hgd")
      .password("1234")
      .tel("010-1234-5678")
      .build();
  }

  public static MessageResponseDto signUpResult() {
    return  MessageResponseDto.builder()
      .message("WELCOME")
      .build();
  }

  // 로그인
  public static MemberRequestDto.loginDto loginMemberDto() {
    return MemberRequestDto.loginDto.builder()
      .email("hgd@gamil.com")
      .password("1234")
      .build();
  }

  public static TokenResponseDto.Token loginResult() {
    return TokenResponseDto.Token.builder()
      .build();
  }

  // 회원 정보 변경
}
