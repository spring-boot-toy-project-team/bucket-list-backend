package com.bucket.list.member.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class Member {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long memberId;

  @Column(unique = true)
  private String email;

  private String nickName;

  private String tel;

  private String introduction;

  private String profileImg;

  private LocalDateTime createdAt = LocalDateTime.now();

  private LocalDateTime modifiedAt = LocalDateTime.now();

  @Enumerated( EnumType.STRING)
  private Active active = Active.MEMBER_ACTIVE;

  private enum Active {
    MEMBER_ACTIVE(1, "활동중"),
    MEMBER_SLEEP(2, "휴면"),
    MEMBER_QUIT(2, "탈퇴");

    private int code;
    private String status;

    Active(int code, String status) {
      this.code = code;
      this.status = status;
    }
  }


}
