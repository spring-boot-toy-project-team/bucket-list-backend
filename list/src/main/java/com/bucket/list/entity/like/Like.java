package com.bucket.list.entity.like;


import com.bucket.list.entity.bucketList.CompletedList;
import com.bucket.list.entity.member.Member;
import com.bucket.list.entity.tag.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "LIKES")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long likeId;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private List<Member> memberList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "COMPLETED_LIST_ID")
    private CompletedList completedList;

    @JoinColumn(name = "TAG_ID")
    private Tag tag;

    public void addMember(Member member){
        this.memberList.add(member);
    }

    public void addCompletedList(CompletedList completedList){
        this.completedList=completedList;
    }

}
