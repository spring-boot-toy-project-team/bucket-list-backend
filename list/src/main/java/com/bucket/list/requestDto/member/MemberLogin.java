package com.bucket.list.requestDto.member;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class MemberLogin {

    @Id
    private String email;

    private String password;
}
