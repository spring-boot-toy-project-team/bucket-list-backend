package com.bucket.list.bucketList.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class BucketListGroup {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long bucketListGroupId;

  @Column(name = "CREATED_YEAR")
  private int year = LocalDateTime.now().getYear();

  private String title;

  @OneToMany(mappedBy = "bucketListGroup")
  private List<BucketList> bucketLists = new ArrayList<>();

  public void addBucketList(BucketList bucketList) {
    if(!this.bucketLists.contains(bucketList)) {
      this.bucketLists.add(bucketList);
    }
  }
}
