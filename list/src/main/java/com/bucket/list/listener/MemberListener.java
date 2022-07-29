package com.bucket.list.listener;

import com.bucket.list.member.entity.Member;
import com.bucket.list.member.entity.Roles;

import javax.persistence.PrePersist;

public class MemberListener {
  @PrePersist
  public void persist(Member member) {
    member.setMemberStatus(Member.MemberStatus.MEMBER_ACTIVE);
    member.setRoles(Roles.ROLE_USER.toString());
  }
}
