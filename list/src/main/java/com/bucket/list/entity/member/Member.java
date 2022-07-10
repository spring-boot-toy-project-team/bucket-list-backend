package com.bucket.list.entity.member;

import com.bucket.list.entity.comments.Comments;
import com.bucket.list.entity.comments.ReComments;
import com.bucket.list.entity.follower.Follower;
import com.bucket.list.entity.like.Like;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Member {

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
    private String profileimg;


    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime modifiedAt = LocalDateTime.now();

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
}
