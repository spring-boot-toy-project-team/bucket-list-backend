package com.bucket.list.bucketList.repository;

import com.bucket.list.bucketList.entity.BucketListGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BucketListGroupRepository extends JpaRepository<BucketListGroup, Long> {
    @Query(value = "select " +
            "* " +
            "from BUCKET_LIST_GROUP " +
            "where 1 = 1 " +
            "and BUCKET_LIST_GROUP_ID = :bucketListGroupId " +
            "and MEMBER_ID = :memberId", nativeQuery = true)
    Optional<BucketListGroup> findByIdAndMemberId(@Param("bucketListGroupId") long bucketListGroupId,
                                                  @Param("memberId") long memberId);

    @Query(value = "select * from BUCKET_LIST_GROUP where MEMBER_ID = :memberId", nativeQuery = true)
    Page<BucketListGroup> findAllByMemberId(@Param("memberId") long memberId, Pageable pageable);
}

