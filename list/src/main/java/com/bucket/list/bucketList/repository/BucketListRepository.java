package com.bucket.list.bucketList.repository;

import com.bucket.list.bucketList.entity.BucketList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BucketListRepository extends JpaRepository<BucketList, Long> {
    @Query(value = "select * from BUCKET_LIST bl where bl.BUCKET_LIST_GROUP_ID = :groupId and bl.BUCKET_LIST_ID = :bucketListId", nativeQuery = true)
    Optional<BucketList> findByBucketListGroupIdAndId(@Param(value = "groupId") long groupId, @Param(value = "bucketListId") long bucketListId);

    @Query(value = "select * from BUCKET_LIST bl where bl.BUCKET_LIST_GROUP_ID = :groupId", nativeQuery = true)
    Page<BucketList> findAllByBucketListGroupId(@Param(value = "groupId") long groupId, Pageable pageable);
}
