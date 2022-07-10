package com.bucket.list.entity.bucketList;


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
public class BucketListGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long bucketListGroupId;

    //처음 BuckListGroup을 만들때 한 번만 적는다
    @Column(name = "CREATED_YEAR")
    private int year = LocalDateTime.now().getYear();

    @Column(length = 300)
    private String title;

//    @OneToMany(mappedBy = "bucketList")
//    private BucketList bucketList;

    @OneToMany(mappedBy = "bucketListGroup")
    private List<BucketList> bucketLists = new ArrayList<>();

    public void addBucketList(BucketList bucketList){
        this.bucketLists.add(bucketList);
    }
}
