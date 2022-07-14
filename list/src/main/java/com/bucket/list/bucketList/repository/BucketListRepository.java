package com.bucket.list.bucketList.repository;

import com.bucket.list.bucketList.entity.BucketList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BucketListRepository extends JpaRepository<BucketList, Long> {
}
