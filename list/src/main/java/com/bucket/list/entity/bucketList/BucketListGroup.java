package com.bucket.list.entity.bucketList;


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
@Table(name = "BUCKET_LIST_GROUP")
public class BucketListGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long buckListGroupId;

    //처음 BuckListGroup을 만들때 한 번만 적는다
    private long year;

    @Column(length = 300)
    private String title;

    @OneToMany(mappedBy = "bucketList")
    private List<BucketList> bucketLists = new ArrayList<>();

    public void addBucketList(BucketList bucketList){
        this.bucketLists.add(bucketList);
    }
}
