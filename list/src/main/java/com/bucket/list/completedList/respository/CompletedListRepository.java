package com.bucket.list.completedList.respository;

import com.bucket.list.completedList.entity.CompletedList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

  @Query(value = "select * " +
          "from COMPLETED_LIST " +
          "where 1 = 1 " +
          "and MEMBER_ID = :memberId", nativeQuery = true)
  Page<CompletedList> findAllByMemberId(@Param("memberId") long memberId, Pageable pageable);

  @Query(value = "select CL.* " +
          "from COMPLETED_LIST CL " +
          "inner join MEMBER M " +
          "on CL.MEMBER_ID = M.MEMBER_ID " +
          "where M.NICK_NAME like %:nickName%", nativeQuery = true)
  Page<CompletedList> findAllByNickName(@Param("nickName") String nickName, Pageable pageable);

  @Query(value = "select * " +
          "from COMPLETED_LIST " +
          "where TAGS like '%'||:tagName||'%'", nativeQuery = true)
  Page<CompletedList> findAllByTagName(@Param("tagName") String tagName, Pageable pageable);
}
//
//  @Query(value = "select COMPLETED_LIST CL.* " +
//          "from COMPLETED_LIST CL " +
//          "inner join MEMBER M" +
//          "on CL.MEMBER_ID = M.MEMBER_ID " +
//          "where M.NICK_NAME = :nickName")
//  Page<CompletedList> findAllByNickName(@Param("nickName") String nickName, Pageable pageable);
//}