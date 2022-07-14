package com.bucket.list.bucketList.entity;

import com.bucket.list.completedList.entity.CompletedList;
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
