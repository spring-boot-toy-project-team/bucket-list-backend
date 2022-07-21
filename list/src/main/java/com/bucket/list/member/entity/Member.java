package com.bucket.list.member.entity;

<<<<<<< HEAD
import com.bucket.list.audit.Auditable;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@NoArgsConstructor
public class Member extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long memberId;

//    @Column(length = 300, unique = true)
//    private String password;

    @Column(length = 200, unique = true)
    private String email;

    @Column(length = 100, unique = true)
    private String nickName;

    @Column(length = 50, unique = true)
    private String tel;

    @Column(length = 500)
    private String profileImg;

    private String introduction;

    @Column(unique = true)
    private String password;
    

    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;

    public enum MemberStatus {
        MEMBER_ACTIVE(1,"활동중"),
        MEMBER_SLEEP(2,"휴면"),
        MEMBER_QUIT(3,"탈퇴");

        @Getter
        private String status;
        private int code;

        MemberStatus(int code, String status) {
            this.status = status;
            this.code=code;
        }
    }
=======
import com.bucket.list.bucketList.entity.BucketListGroup;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Member {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long memberId;

  @Column(unique = true, length = 200)
  private String email;

  private String nickName;

  @Column(length = 50, unique = true)
  private String tel;

  @Column(columnDefinition = "TEXT")
  private String introduction;

  private String profileImg;

  private LocalDateTime createdAt = LocalDateTime.now();

  private LocalDateTime modifiedAt = LocalDateTime.now();

  @Enumerated( EnumType.STRING)
  private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;

  @OneToMany(mappedBy = "member")
  List<BucketListGroup> bucketListGroups = new ArrayList<>();

  public void addBucketListGroup(BucketListGroup bucketListGroup) {
    if(!this.bucketListGroups.contains(bucketListGroup)) {
      this.bucketListGroups.add(bucketListGroup);
    }
  }

  private enum MemberStatus {
    MEMBER_ACTIVE(1, "활동중"),
    MEMBER_SLEEP(2, "휴면"),
    MEMBER_QUIT(2, "탈퇴");

    private int code;
    private String status;

    MemberStatus(int code, String status) {
      this.code = code;
      this.status = status;
    }
  }


>>>>>>> main
}
