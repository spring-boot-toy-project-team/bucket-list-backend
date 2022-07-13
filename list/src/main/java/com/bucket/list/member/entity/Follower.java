package com.bucket.list.member.entity;

import com.bucket.list.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Follower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long followerId;

    @ManyToOne
    @JoinColumn(name = "FROM_MEMBER_ID")
    private Member fromMember;

    @ManyToOne
    @JoinColumn(name = "TO_MEMBER_ID")
    private Member toMember;

    public void addFromMember(Member fromMember){
        this.fromMember = fromMember;
    }

    public void addTOMember(Member toMember){
        this.toMember = toMember;
    }
}
