package com.bucket.list.repository.bucketList;

import com.bucket.list.entity.bucketList.BucketList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BucketListRepository extends JpaRepository<BucketList, Long> {
    Optional<BucketList> findByBucketListId(long bucketListId);
}
