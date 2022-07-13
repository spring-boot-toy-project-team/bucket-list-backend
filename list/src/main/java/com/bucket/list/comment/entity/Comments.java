package com.bucket.list.comment.entity;

import com.bucket.list.completedList.entity.CompletedList;
import com.bucket.list.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Comments {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long commentsId;

  @Column(columnDefinition = "TEXT")
  private String contents;

  private LocalDateTime createdAt = LocalDateTime.now();

  private LocalDateTime modifiedAt = LocalDateTime.now();

  @ManyToOne
  @JoinColumn(name = "COMPLETED_LIST_ID")
  private CompletedList completedList;

  @OneToMany(mappedBy = "comments")
  private List<ReComments> reComments = new ArrayList<>();

  @ManyToOne
  @JoinColumn(name = "MEMBER_ID")
  private Member member;

  public void addReComments(ReComments reComments) {
    if(!this.reComments.contains(reComments))
      this.addReComments(reComments);
  }

  public void addMember(Member member) {
    this.member = member;
  }

  public void addCompletedList(CompletedList completedList) {
    this.completedList = completedList;
  }
}
