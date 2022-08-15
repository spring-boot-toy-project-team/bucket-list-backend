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

    public static Member getMember(){
        Member member = new Member();
        member.setMemberId(1L);
        member.setEmail("hgd@gamil.com");
        member.setTel("010-1234-5678");
        member.setIntroduction("나다");
        member.setNickName("홍길동");
        return member;
    }

    public static MemberRequestDto.SignUpDto signUpMemberDto(){
        return MemberRequestDto.SignUpDto.builder()
                .email("hgd@gmail.com")
                .nickName("홍길동")
                .password("1234")
                .tel("010-1234-5678")
                .build();
    }

    public static MessageResponseDto signUpResult(){
        return MessageResponseDto.builder()
                .message("WELCOME")
                .build();
    }
    public static MessageResponseDto loginMessage(){
        return MessageResponseDto.builder()
                .message("SUCCESS")
                .build();
    }

    public static MemberRequestDto.loginDto loginMemberDto() {
        return MemberRequestDto.loginDto.builder()
                .email("hgd@gmail.com")
                .password("1234")
                .build();
    }

    public static TokenResponseDto.Token loginResult(){
        return TokenResponseDto.Token.builder()
                .build();
    }

//    public static MemberResponseDto.MemberInfo login(){
//        return MemberResponseDto.MemberInfo.builder()
//                .email(signUpMemberDto().getEmail())
//                .tel(signUpMemberDto().getTel())
//                .nickName(signUpMemberDto().getNickName())
//                .introduction(signUpMemberDto().getIntroduction())
//                .profileImg(signUpMemberDto().getProfileImg())
//                .build();
//    }
}
