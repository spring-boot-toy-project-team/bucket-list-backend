package com.bucket.list.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Getter
@AllArgsConstructor
public class MemberResponseDto {

    //회원가입
    @Getter
    @AllArgsConstructor
    public static class memberSignUp{
        private String message;

        //확인용
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long memberId;

    }

    //회원 탈퇴
    @Getter
    @NoArgsConstructor
    public static class memberAccountSecession{
        private String message;
    }

    //회원 정보 조회
    @Getter
    @Setter
    @AllArgsConstructor
    public static class findMember{

        private String password;

        private String nickName;

        private String tel;

        private String introduction;

        private String profileImg;

        private String email;
    }


    //회원 정보 수정
    @Getter
    @Setter
    @AllArgsConstructor
    public static class updateMember{

        private String nickName;

        private String tel;

        private String introduction;

        private String profileImg;

        private String message;

    }


    //로그인
    @Getter
    @NoArgsConstructor
    public static class memberSignIn{
        //Access Token
        //Refresh Token
        private long memberId;

        private String message;
    }

    //로그아웃
    @Getter
    @NoArgsConstructor
    public static class memberLogOut{
        private String message;
    }
}
