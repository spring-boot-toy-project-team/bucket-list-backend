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
public class CompletedList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long completedListId;

    @OneToOne
    @JoinColumn(name = "BUCKET_LIST_ID")
    private BucketList bucketList;

    @OneToMany(mappedBy = "completedList")
    private List<CompletedListTag> completedListTagList = new ArrayList<>();

    @OneToMany(mappedBy = "completedList")
    private List<Comments> commentsList = new ArrayList<>();

    @OneToMany(mappedBy = "completedList")
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "completedList")
    private List<Img> imgList = new ArrayList<>();

    private String contents;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime modifiedAt = LocalDateTime.now();

    private long likeCount;

    public void addComment(Comments comments){
        this.commentsList.add(comments);
    }

    public void addLike(Like like){
        if(!this.likes.contains(like)){
            this.likes.add(like);
        }
    }

    public void addCompletedListTag(CompletedListTag completedListTag){
        this.completedListTagList.add(completedListTag);
    }

    public void addImg(Img img){
        this.imgList.add(img);
    }

}
