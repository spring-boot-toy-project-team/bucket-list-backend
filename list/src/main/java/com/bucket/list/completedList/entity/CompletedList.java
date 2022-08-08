package com.bucket.list.completedList.entity;

import com.bucket.list.bucketList.entity.BucketList;
import com.bucket.list.comment.entity.Comments;
import com.bucket.list.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class CompletedList {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long completedListId;

  @Column(columnDefinition = "TEXT")
  private String contents;

  private LocalDateTime createdAt = LocalDateTime.now();

  private LocalDateTime modifiedAt = LocalDateTime.now();

  @OneToMany(mappedBy = "completedList")
  List<Img> imgs = new ArrayList<>();

  @Column(columnDefinition = "TEXT")
  private String tags;

  @OneToOne
  @JoinColumn(name = "BUCKET_LIST_ID")
  private BucketList bucketList;

  @OneToMany(mappedBy = "completedList", cascade = CascadeType.ALL)
  private List<Comments> comments = new ArrayList<>();

  @OneToMany(mappedBy = "completedList", cascade = CascadeType.ALL)
  private List<Like> likes = new ArrayList<>();

  @ManyToOne
  @JoinColumn(name = "MEMBER_ID")
  Member member;

  public void addMember(Member member) {
    this.member = member;
  }

  public void addLike(Like like) {
    if(!this.likes.contains(like)) {
      this.likes.add(like);
    }
  }

  public void addComments(Comments comments) {
    if(!this.comments.contains(comments)) {
      this.comments.add(comments);
    }
  }

  public void addBucketList(BucketList bucketList) {
    this.bucketList = bucketList;
  }

  public void addImg(Img img) {
    if(!this.imgs.contains(img))
      this.imgs.add(img);
  }
}
