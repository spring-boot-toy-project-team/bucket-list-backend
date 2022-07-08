package com.bucket.list.member;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Member {

    @Id
    private long memberId;

    private String email;

    private String nickName;

    @Column(unique = true)
    private String tel;

    private String introduction;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime modifiedAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private Active active;

    private enum Active{
        ACTIVE("활동중"),
        ACTIVE("휴먼"),
        ACTIVE("탈퇴");

        private String status;

        Active(String status) {
            this.status = status;
        }
    }

}
