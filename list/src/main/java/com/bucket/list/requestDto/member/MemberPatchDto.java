package com.bucket.list.requestDto.member;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
public class MemberPatchDto {

    private long memberId;

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }
}
