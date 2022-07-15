package com.bucket.list.comments.entity;


import com.bucket.list.audit.Auditable;
import com.bucket.list.completList.entity.CompletedList;
import com.bucket.list.member.entity.Member;
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
public class Comments extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentsId;

    @ManyToOne
    @JoinColumn(name = "COMPLETED_LIST_ID")
    private CompletedList completedList;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "comments")
    private List<ReComments> reComments = new ArrayList<>();

    private String contents;


    public void addMember(Member member){
        this.member=member;
    }

    public void addCompletedList(CompletedList completedList){
        this.completedList = completedList;
    }

}
