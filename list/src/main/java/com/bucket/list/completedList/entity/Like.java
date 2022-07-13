package com.bucket.list.completedList.entity;

import com.bucket.list.completedList.entity.CompletedList;
import com.bucket.list.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "LIKES")
public class Like {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long likeId;

  @ManyToOne
  @JoinColumn(name = "MEMBER_ID")
  private Member member;

  @ManyToOne
  @JoinColumn(name = "COMPLETED_LIST_ID")
  private CompletedList completedList;

  public void addCompletedListList(CompletedList completedList) {
    this.completedList = completedList;
  }

  public void addMember(Member member) {
    this.member = member;
  }
}
