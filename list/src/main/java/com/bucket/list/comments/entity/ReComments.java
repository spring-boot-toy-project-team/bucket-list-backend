package com.bucket.list.comments.entity;


import com.bucket.list.audit.Auditable;
import com.bucket.list.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ReComments extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reCommentsId;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

//    @JoinColumn(name = "MEMBER_ID")
//    private Member memberId2;

    @ManyToOne
    @JoinColumn(name = "COMMENTS_ID")
    private Comments comments;

    private String contents;

    public void addMember(Member member){
        this.member=member;
    }

    public void addComments(Comments comments){
        this.comments=comments;
    }
}
