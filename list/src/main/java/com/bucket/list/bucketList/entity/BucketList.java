package com.bucket.list.bucketList.entity;

import com.bucket.list.completedList.entity.CompletedList;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class BucketList {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long bucketListId;

  private String target;

  private boolean isCompleted;

  @ManyToOne
  @JoinColumn(name = "BUCKET_LIST_GROUP_ID")
  private BucketListGroup bucketListGroup;

  @OneToOne(mappedBy = "bucketList")
  private CompletedList completedList;

  public void addCompletedList(CompletedList completedList) {
    this.completedList = completedList;
  }

  public void addBucketListGroup(BucketListGroup bucketListGroup) {
    this.bucketListGroup = bucketListGroup;
  }
}
