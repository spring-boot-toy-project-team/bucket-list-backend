package com.bucket.list.stub.member;

import com.bucket.list.dto.response.MessageResponseDto;
import com.bucket.list.dto.token.TokenResponseDto;
import com.bucket.list.member.dto.MemberRequestDto;
import com.bucket.list.member.dto.MemberResponseDto;
import com.bucket.list.member.entity.Member;
import com.bucket.list.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class MemberStub {
    private static MemberMapper mapper;
    private static PasswordEncoder passwordEncoder;

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

    // 회원가입 결과
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


    // 회원 정보 조회
    public static MemberResponseDto.MemberInfo getMemberInfo(Member member) {
        return MemberResponseDto.MemberInfo.builder()
                .email(member.getEmail())
                .introduction(member.getIntroduction())
                .tel(member.getTel())
                .nickName(member.getNickName())
                .build();
    }

    // 회원 정보 변경
    public static MemberRequestDto.UpdateDto updateMember(Member member) {
        return MemberRequestDto.UpdateDto.builder()
                .memberId(member.getMemberId())
                .password("1234")
                .tel("010-1234-5678")
                .status(Member.MemberStatus.MEMBER_SLEEP)
                .nickName("hgd")
                .introduction("hey")
                .profileImg(null)
                .build();
    }

    public static MemberResponseDto.UpdateDto getUpdateMemberInfo(Member member) {
        return MemberResponseDto.UpdateDto.builder()
                .memberId(member.getMemberId())
                .nickName("hgd")
                .introduction("hey")
                .tel("010-1234-5678")
                .build();
    }
    public static MessageResponseDto loginMessage(){
        return MessageResponseDto.builder()
                .message("SUCCESS")
                .build();
    }
}
