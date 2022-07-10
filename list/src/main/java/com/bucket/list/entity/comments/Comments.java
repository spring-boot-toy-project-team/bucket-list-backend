package com.bucket.list.entity.comments;


import com.bucket.list.entity.bucketList.CompletedList;
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
@Table(name = "COMMENTS")
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;

    @ManyToOne
    @JoinColumn(name = "COMPLETED_LIST ID")
    private CompletedList completedList;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "reComments")
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
