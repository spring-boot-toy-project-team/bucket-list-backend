package com.bucket.list.completList.entity;


import com.bucket.list.audit.Auditable;
import com.bucket.list.bucketList.entity.BucketList;
import com.bucket.list.comments.entity.Comments;
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
public class CompletedList extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long completedListId;

    @OneToOne
    @JoinColumn(name = "BUCKET_LIST_ID")
    private BucketList bucketList;

    @OneToMany(mappedBy = "completedList", cascade = CascadeType.ALL)
    private String tags;

    @OneToMany(mappedBy = "completedList")
    private List<Comments> comments = new ArrayList<>();

    @OneToMany(mappedBy = "completedList")
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "completedList")
    private List<Img> imgs = new ArrayList<>();

    @Column(columnDefinition = "TEXT")
    private String contents;

    private long likeCount;

    public void addComments(Comments comments) {
        if(!this.comments.contains(comments)) {
            this.comments.add(comments);
        }
    }

    public void addLike(Like like){
        if(!this.likes.contains(like)){
            this.likes.add(like);
        }
    }


    public void addImg(Img img) {
        if(!this.imgs.contains(img))
            this.imgs.add(img);
    }

    public void addBucketList(BucketList bucketList) {
        this.bucketList = bucketList;
    }

}
