package com.bucket.list.completedList.entity;

import com.bucket.list.bucketList.entity.BucketList;
import com.bucket.list.comment.entity.Comments;
import com.bucket.list.img.entity.Img;
import com.bucket.list.like.entity.Like;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class CompletedList {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long completedId;

  private String contents;

  private LocalDateTime createdAt = LocalDateTime.now();

  private LocalDateTime modifiedAt = LocalDateTime.now();

  @OneToMany(mappedBy = "completedList")
  List<Img> imgs = new ArrayList<>();

  @OneToMany(mappedBy = "completedList")
  private List<CompletedListTag> completedListTags;

  @OneToOne
  @JoinColumn(name = "BUCKET_LIST_ID")
  private BucketList bucketList;

  @OneToMany(mappedBy = "completedList")
  private List<Comments> comments = new ArrayList<>();

  @OneToMany(mappedBy = "completedList")
  private List<Like> likes = new ArrayList<>();

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

  public void addCompletedListTag(CompletedListTag completedListTag) {
    if(!completedListTags.contains(completedListTag))
      this.completedListTags.add(completedListTag);
  }

  public void addImg(Img img) {
    if(!this.imgs.contains(img))
      this.imgs.add(img);
  }
}
