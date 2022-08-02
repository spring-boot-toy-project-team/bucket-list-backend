package com.bucket.list.comment.repository;

import com.bucket.list.comment.entity.Comments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {

  @Query(value = "select * from COMMENTS where COMPLETED_LIST_ID = :completedListId", nativeQuery = true)
  Page<Comments> findAllByCompletedListId(@Param("completedListId") long completedListId, PageRequest comments_id);

  @Query(value = "select * from COMMENTS where COMMENTS_ID = :commentsId and MEMBER_ID = :memberId and COMPLETED_LIST_ID = :completedListId", nativeQuery = true)
  Optional<Comments> findByIdAndCompletedListIdAndMemberId(@Param("commentsId") long commentsId,
                                                           @Param("completedListId") long completedListId,
                                                           @Param("memberId") long memberId);
}
