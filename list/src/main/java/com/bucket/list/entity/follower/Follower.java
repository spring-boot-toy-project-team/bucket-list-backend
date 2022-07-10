package com.bucket.list.entity.follower;

import com.bucket.list.entity.member.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
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
