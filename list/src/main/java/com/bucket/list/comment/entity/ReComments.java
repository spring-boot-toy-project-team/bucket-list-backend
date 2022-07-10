package com.bucket.list.comment.entity;

import com.bucket.list.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class ReComments {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long reCommentsId;

  @Column(columnDefinition = "TEXT")
  private String contents;

  private LocalDateTime createdAt = LocalDateTime.now();

  private LocalDateTime modifiedAt = LocalDateTime.now();

  @ManyToOne
  @JoinColumn(name = "MEMBER_ID")
  private Member member;

  @ManyToOne
  @JoinColumn(name = "COMMENTS_ID")
  private Comments comments;

  public void addMember(Member member) {
    this.member = member;
  }

  public void addComments(Comments comments) {
    this.comments = comments;
  }
}
