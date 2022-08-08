package com.bucket.list.completedList.respository;

import com.bucket.list.completedList.entity.CompletedList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CompletedListRepository extends JpaRepository<CompletedList, Long> {
  @Query(value = "select * from COMPLETED_LIST where COMPLETED_LIST_ID = :completedListId " +
          "and BUCKET_LIST_ID = :bucketListId", nativeQuery = true)
  Optional<CompletedList> findByCompletedListIdAndBucketListId(@Param("completedListId") long completedListId,
                                                               @Param("bucketListId") long bucketListId);

  @Query(value = "select * from COMPLETED_LIST where BUCKET_LIST_ID = :bucketListId", nativeQuery = true)
  Optional<CompletedList> findByBucketListId(@Param("bucketListId") long bucketListId);

  @Modifying(clearAutomatically = true)
  CompletedList save(CompletedList completedList);
}
