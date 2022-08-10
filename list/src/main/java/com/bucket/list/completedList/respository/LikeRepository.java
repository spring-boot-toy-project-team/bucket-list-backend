package com.bucket.list.completedList.respository;

import com.bucket.list.completedList.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
  @Query(value = "select * " +
    "from LIKES " +
    "where 1 = 1 " +
    "and COMPLETED_LIST_ID = :completedListId " +
    "and MEMBER_ID = :memberId", nativeQuery = true)
  Optional<Like> findByCompletedListIdAndMemberId(@Param("completedListId") long completedListId,
                                                  @Param("memberId") long memberId);
}
