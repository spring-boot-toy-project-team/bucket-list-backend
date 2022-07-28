package com.bucket.list.listener;

import com.bucket.list.member.entity.Member;

import javax.persistence.PrePersist;

public class MemberListner {

    @PrePersist
    public void persist(Member member){
        member.setMemberStatus(Member.MemberStatus.MEMBER_ACTIVE);
        member.setRoles(member.getRoles());
    }
}
