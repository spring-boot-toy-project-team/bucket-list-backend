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
    @JoinColumn(name = "MEMBER_ID")
    private Member memberId;
}
