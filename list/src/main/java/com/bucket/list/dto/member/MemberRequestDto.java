package com.bucket.list.dto.member;

import com.bucket.list.entity.member.Member;
import lombok.*;

import javax.persistence.Id;
import javax.validation.constraints.Email;


public class MemberRequestDto {

    //회원가입
    @Getter
    public static class memberSignUpDto{
        @Email
        private String email;

        private String nickName;

        private String introduction;

        private String profileImg;

        private String password;

        private String tel;
    }

    //회원 탈퇴
    @Getter
    public static class memberAccountSecession{
        @Id
        private long memberId;
    }

    //회원 정보 조회
    @Getter
    @AllArgsConstructor
    public static class findMember{
        @Id
        private long memberId;
    }

    //회원 정보 수정
    @Getter
    @Setter
    public static class updateMember{
        private String password;

        private String nickName;

        private String tel;

        private String introduction;

        private String profileImg;

        private long memberId;
    }

    //로그인
    @Getter
    public static class memberSignIn{
        private String email;

        private String password;
    }

    //로그아웃
    @Getter
    public static class memberLogOut{
        private long memberId;
    }
}
