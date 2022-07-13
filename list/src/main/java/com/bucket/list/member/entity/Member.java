package com.bucket.list.member.entity;

import com.bucket.list.bucketList.entity.BucketListGroup;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Member {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long memberId;

  @Column(unique = true, length = 200)
  private String email;

  @JsonIgnore
  private String password;

  private String nickName;

  @Column(length = 50, unique = true)
  private String tel;

  @Column(columnDefinition = "TEXT")
  private String introduction;

  private String profileImg;

  private LocalDateTime createdAt = LocalDateTime.now();

  private LocalDateTime modifiedAt = LocalDateTime.now();

  @Enumerated(value = EnumType.STRING)
  @Column(length = 20, nullable = false)
  private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;

  @OneToMany(mappedBy = "member")
  List<BucketListGroup> bucketListGroups = new ArrayList<>();

  public void addBucketListGroup(BucketListGroup bucketListGroup) {
    if(!this.bucketListGroups.contains(bucketListGroup)) {
      this.bucketListGroups.add(bucketListGroup);
    }
  }

  public enum MemberStatus {
    MEMBER_ACTIVE("활동중"),
    MEMBER_SLEEP("휴면"),
    MEMBER_QUIT("탈퇴");

    private String status;

    MemberStatus(String status) {
      this.status = status;
    }
  }


}
