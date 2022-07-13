package com.bucket.list.completList.entity;


import com.bucket.list.completList.entity.CompletedList;
import com.bucket.list.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "LIKES")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long likeId;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "COMPLETED_LIST_ID")
    private CompletedList completedList;

    public void addMember(Member member){
        this.member=member;
    }

    public void addCompletedList(CompletedList completedList){
        this.completedList=completedList;
    }

}
