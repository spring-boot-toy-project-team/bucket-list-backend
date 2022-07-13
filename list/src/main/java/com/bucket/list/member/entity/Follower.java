package com.bucket.list.member.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Follower {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long followerId;

  @ManyToOne
  @JoinColumn(name = "FROM_MEMBER_ID")
  private Member fromMember;

  @ManyToOne
  @JoinColumn(name = "TO_MEMBER_ID")
  private Member toMember;

  public void addFromMember(Member fromMember) {
    this.fromMember = fromMember;
  }

  public void addToMember(Member toMember) {
    this.toMember = toMember;
  }
}
