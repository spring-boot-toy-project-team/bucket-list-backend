package com.bucket.list.entity.bucketList;


import com.bucket.list.entity.comments.Comments;
import com.bucket.list.entity.img.Img;
import com.bucket.list.entity.like.Like;
import com.bucket.list.entity.tag.CompletedListTag;
import com.bucket.list.entity.tag.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "COMPLETED_LIST")
public class CompletedList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long completedListId;

    @OneToOne
    @JoinColumn(name = "BUCKET_LIST_ID")
    private BucketList bucketList;

    @OneToMany(mappedBy = "completedBucketList")
    private List<Tag> tags = new ArrayList<>();

    @OneToMany(mappedBy = "completedListTag")
    private List<CompletedListTag> completedListTagList = new ArrayList<>();

    @OneToMany(mappedBy = "comments")
    private List<Comments> commentsList = new ArrayList<>();

    @OneToMany(mappedBy = "likes")
    private List<Like> likeList = new ArrayList<>();

    @OneToMany(mappedBy = "img")
    private List<Img> imgList = new ArrayList<>();

    private String contents;

    private LocalDateTime completedAt = LocalDateTime.now();

    private Integer likeCount;

    public void addComment(Comments comments){
        this.commentsList.add(comments);
    }

    public void addLike(Like like){
        this.likeList.add(like);
    }

    public void addCompletedListTag(CompletedListTag completedListTag){
        this.completedListTagList.add(completedListTag);
    }

    public void addImg(Img img){
        this.imgList.add(img);
    }
}
