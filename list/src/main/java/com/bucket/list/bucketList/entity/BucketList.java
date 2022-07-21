package com.bucket.list.bucketList.entity;

<<<<<<< HEAD
import com.bucket.list.completList.entity.CompletedList;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
=======
import com.bucket.list.completedList.entity.CompletedList;
import lombok.Getter;
import lombok.NoArgsConstructor;
>>>>>>> main

import javax.persistence.*;

@Getter
<<<<<<< HEAD
@Setter
@Entity
@NoArgsConstructor
public class BucketList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bucketListId;

    @ManyToOne
    @JoinColumn(name = "BUCKET_LIST_GROUP_ID")
    private BucketListGroup bucketListGroup;

    @OneToOne(mappedBy = "bucketList",cascade = CascadeType.ALL)
    private CompletedList completedList;


    @Column(length = 500)
    private String target;

    private boolean completed;

    public boolean getCompleted() {
        return this.completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }




    public void addBucketListGroup(BucketListGroup bucketListGroup){
        this.bucketListGroup = bucketListGroup;
    }

    public void addCompletedList(CompletedList completedList){
        this.completedList=completedList;
    }
=======
@NoArgsConstructor
@Entity
public class BucketList {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long bucketListId;

  @Column(length = 500)
  private String target;

  private boolean isCompleted;

  @ManyToOne
  @JoinColumn(name = "BUCKET_LIST_GROUP_ID")
  private BucketListGroup bucketListGroup;

  @OneToOne(mappedBy = "bucketList")
  private CompletedList completedList;

  public void addCompletedList(CompletedList completedList) {
    this.completedList = completedList;
  }

  public void addBucketListGroup(BucketListGroup bucketListGroup) {
    this.bucketListGroup = bucketListGroup;
  }
>>>>>>> main
}
