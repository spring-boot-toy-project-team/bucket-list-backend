package com.bucket.list.entity.bucketList.repository;

import com.bucket.list.entity.bucketList.BucketListGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BucketListGroupRepository extends JpaRepository<BucketListGroup, Long> {
    Optional<BucketListGroup> findByBucketListGroupId(long bucketListGroupId);
}
