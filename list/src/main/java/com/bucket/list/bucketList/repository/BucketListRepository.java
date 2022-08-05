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
    "where BL.BUCKET_LIST_GROUP_ID = " +
      "(select BLG.groupId " +
      "from BUCKET_LIST_GROUP BLG " +
      "where BLG.BUCKET_LIST_GROUP_ID = :groupId " +
      "and BLG.MEMBER_ID = :memberId)", nativeQuery = true)
  Page<BucketList> findAllByBucketListGroup(@Param(value = "groupId") long groupId,
                                            @Param(value = "memberId") long memberId, Pageable pageable);

  @Query(value = "select " +
    "BL.* " +
    "from BUCKET_LIST BL, BUCKET_LIST_GROUP BLG " +
    "where BL.BUCKET_LIST_GROUP_ID = :groupId " +
    "and BLG.BUCKET_LIST_GROUP_ID = :groupId" +
    "and BL.BUCKET_LIST_ID = :bucketListId" +
    "and BLG.MEMBER_ID = :memberId", nativeQuery = true)
  Optional<BucketList> findByIdAndBucketListGroupIdAndMemberId(@Param("bucketListId") long bucketListId,
                                                               @Param(value = "groupId") long groupId,
                                                               @Param("memberId") long memberId);
}
