package com.bucket.list.entity.comments;


import com.bucket.list.entity.bucketList.CompletedList;
import com.bucket.list.entity.like.Like;
import com.bucket.list.entity.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime modifiedAt = LocalDateTime.now();

    public void addMember(Member member){
        this.member=member;
    }

    public void addCompletedList(CompletedList completedList){
        this.completedList = completedList;
    }

}
