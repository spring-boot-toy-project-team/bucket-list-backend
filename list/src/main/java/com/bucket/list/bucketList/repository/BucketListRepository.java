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
  @Query(value = "select * from BUCKET_LIST where BUCKET_LIST_GROUP_ID = :groupId and BUCKET_LIST_ID = :bucketListId", nativeQuery = true)
  Optional<BucketList> findByBucketListGroupIdAndId(@Param(value = "groupId") long groupId, @Param("bucketListId") long bucketListId);

  @Query(value = "select " +
    "BL.* " +
    "from BUCKET_LIST BL " +
    "inner join BUCKET_LIST_GROUP BLG " +
    "on BL.BUCKET_LIST_GROUP_ID = BLG.BUCKET_LIST_GROUP_ID " +
    "where 1=1 " +
    "and BLG.BUCKET_LIST_GROUP_ID = :groupId " +
    "and BLG.MEMBER_ID = :memberId", nativeQuery = true)
  Page<BucketList> findAllByBucketListGroupId(@Param(value = "groupId") long groupId,
                                            @Param(value = "memberId") long memberId, Pageable pageable);

  @Query(value = "select " +
    "BL.* " +
    "from BUCKET_LIST BL " +
    "inner join BUCKET_LIST_GROUP BLG " +
    "on BL.BUCKET_LIST_GROUP_ID = BLG.BUCKET_LIST_GROUP_ID " +
    "where 1=1 " +
    "and BL.BUCKET_LIST_ID = :bucketListId " +
    "and BLG.BUCKET_LIST_GROUP_ID = :groupId " +
    "and BLG.MEMBER_ID = :memberId", nativeQuery = true)
  Optional<BucketList> findByIdAndBucketListGroupIdAndMemberId(@Param("bucketListId") long bucketListId,
                                                               @Param(value = "groupId") long groupId,
                                                               @Param("memberId") long memberId);

  @Query(value = "select * " +
    "from BUCKET_LIST " +
    "where 1 = 1 " +
    "and BUCKET_LIST_ID = :bucketListId " +
    "and MEMBER_ID = :memberId", nativeQuery = true)
  Optional<BucketList> findByIdAndAndMemberId(@Param("bucketListId") long bucketListId, @Param("memberId") long memberId);
}
