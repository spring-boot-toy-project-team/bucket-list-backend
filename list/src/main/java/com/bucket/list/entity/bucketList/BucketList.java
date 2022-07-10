package com.bucket.list.entity.bucketList;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "BUCKET_LIST")
public class BucketList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long buckListId;

    @ManyToOne
    @JoinColumn(name = "BUCKET_LIST_GROUP_ID")
    private BucketListGroup bucketListGroup;

    @OneToMany(mappedBy = "bucketList")
    private BucketList bucketList;

    @Column(length = 500)
    private String target;

    private Boolean isComplete;

    public void addBucketListGroup(BucketListGroup bucketListGroup){
        this.bucketListGroup = bucketListGroup;
    }
}
