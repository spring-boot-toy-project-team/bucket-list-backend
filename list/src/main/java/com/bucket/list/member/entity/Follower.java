package com.bucket.list.member.entity;

<<<<<<< HEAD
import com.bucket.list.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
=======
import lombok.Getter;
import lombok.NoArgsConstructor;
>>>>>>> main

import javax.persistence.*;

@Getter
<<<<<<< HEAD
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
=======
@NoArgsConstructor
@Entity
public class Follower {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long followerId;

  @ManyToOne
  @JoinColumn(name = "FROM_MEMBER_ID")
  private Member fromMember;

  @ManyToOne
  @JoinColumn(name = "TO_MEMBER_ID")
  private Member toMember;

  public void addFromMember(Member fromMember) {
    this.fromMember = fromMember;
  }

  public void addToMember(Member toMember) {
    this.toMember = toMember;
  }
>>>>>>> main
}
