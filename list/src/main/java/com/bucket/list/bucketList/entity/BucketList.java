package com.bucket.list.bucketList.entity;

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
public class BucketList {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long bucketListId;

  @Column(length = 500)
  private String target;

  private Boolean completed = false;

  @ManyToOne
  @JoinColumn(name = "BUCKET_LIST_GROUP_ID")
  private BucketListGroup bucketListGroup;

  @OneToOne(mappedBy = "bucketList")
  private CompletedList completedList;

  @ManyToOne
  @JoinColumn(name = "MEMBER_ID")
  Member member;

  public boolean getCompleted() {
    return this.completed;
  }


  public void addBucketListGroup(BucketListGroup bucketListGroup) {
    this.bucketListGroup = bucketListGroup;
  }

  public void addMember(Member member){
    this.member = member;
  }


}
