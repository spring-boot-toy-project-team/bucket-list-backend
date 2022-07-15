package com.bucket.list.bucketList.entity;

import com.bucket.list.listener.YearListener;
import com.bucket.list.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@EntityListeners(value = YearListener.class)
public class BucketListGroup {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long bucketListGroupId;

  @Column(name = "CREATED_YEAR")
  private int year;

  @Column(length = 300)
  private String title;

  @OneToMany(mappedBy = "bucketListGroup")
  private List<BucketList> bucketLists = new ArrayList<>();

  @ManyToOne
  @JoinColumn(name = "MEMBER_ID")
  private Member member;

  public void addBucketList(BucketList bucketList) {
    if(!this.bucketLists.contains(bucketList)) {
      this.bucketLists.add(bucketList);
    }
  }

  public void addMember(Member member) {
    this.member = member;
  }
}
