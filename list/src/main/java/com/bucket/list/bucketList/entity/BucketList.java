package com.bucket.list.bucketList.entity;

import com.bucket.list.completList.entity.CompletedList;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class BucketList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long buckListId;

    @ManyToOne
    @JoinColumn(name = "BUCKET_LIST_GROUP_ID")
    private BucketListGroup bucketListGroup;

    @OneToOne(mappedBy = "bucketList")
    private CompletedList completedList;


    @Column(length = 500)
    private String target;

    private Boolean isComplete;

    public void addBucketListGroup(BucketListGroup bucketListGroup){
        this.bucketListGroup = bucketListGroup;
    }

    public void addCompletedList(CompletedList completedList){
        this.completedList=completedList;
    }
}