package com.bucket.list.listener;

import com.bucket.list.bucketList.entity.BucketListGroup;

import javax.persistence.PrePersist;
import java.time.LocalDateTime;

public class YearListener {
  @PrePersist
  public void prePersist(BucketListGroup bucketListGroup) {
    bucketListGroup.setYear(LocalDateTime.now().getYear());
  }
}
