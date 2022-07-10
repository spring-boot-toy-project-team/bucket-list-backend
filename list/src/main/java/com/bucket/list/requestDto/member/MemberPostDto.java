package com.bucket.list.requestDto.member;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
public class MemberPostDto {

    @Email
    private String email;

    private String nickName;

    private String introduction;

    private String profileImg;

    private String password;

    private String tel;
}
