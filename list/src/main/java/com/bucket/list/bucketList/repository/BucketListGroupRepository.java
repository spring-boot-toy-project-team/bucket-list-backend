package com.bucket.list.bucketList.repository;

import com.bucket.list.bucketList.entity.BucketListGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BucketListGroupRepository extends JpaRepository<BucketListGroup, Long> {
    Optional<BucketListGroup> findByBucketListGroupId(long bucketListGroupId);
}
