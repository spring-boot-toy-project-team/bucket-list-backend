package com.bucket.list.entity.comments;


import com.bucket.list.entity.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ReComments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reCommentId;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

//    @JoinColumn(name = "MEMBER_ID")
//    private Member memberId2;

    @ManyToOne
    @JoinColumn(name = "COMMENTS_ID")
    private Comments comments;

    private String contents;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime modifiedAt = LocalDateTime.now();

    public void addMember(Member member){
        this.member=member;
    }

    public void addComments(Comments comments){
        this.comments=comments;
    }
}
