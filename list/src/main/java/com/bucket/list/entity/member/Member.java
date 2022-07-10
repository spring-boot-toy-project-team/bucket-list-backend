package com.bucket.list.entity.member;

import com.bucket.list.entity.comments.Comments;
import com.bucket.list.entity.comments.ReComments;
import com.bucket.list.entity.follower.Follower;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "MEMBER")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany(mappedBy = "reComments")
    private List<ReComments> reCommentsList = new ArrayList<>();

    @OneToMany(mappedBy = "follower")
    private List<Follower> followerList = new ArrayList<>();

    @OneToMany(mappedBy = "comments")
    private List<Comments> commentsList = new ArrayList<>();

    @OneToMany(mappedBy = "likes")
    private Map<Member, Long> like = new HashMap<>();

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime modifiedAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;

    public enum MemberStatus {
        MEMBER_ACTIVE("활동중"),
        MEMBER_SLEEP("휴면"),
        MEMBER_QUIT("탈퇴");

        @Getter
        private String status;

        MemberStatus(String status) {
            this.status = status;
        }
    }
}
