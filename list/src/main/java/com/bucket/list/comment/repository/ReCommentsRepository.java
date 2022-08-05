package com.bucket.list.comment.repository;


import com.bucket.list.comment.entity.ReComments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReCommentsRepository extends JpaRepository<ReComments, Long> {
    @Query(value = "select * from RE_COMMENTS where COMMENTS_ID = :commentsId", nativeQuery = true)
    Page<ReComments> findAllByCommentsId(@Param("commentsId") long commentsId, PageRequest re_comments_id);

    @Query(value = "select * from RE_COMMENTS where RE_COMMENTS_ID = :reCommentsId and COMMENTS_ID = :commentsId and MEMBER_ID = :memberId", nativeQuery = true)
    Optional<ReComments> findByIdAndCommentsIdAndMemberId(@Param("commentsId") long commentsId,
                                                          @Param("reCommentsId") long reCommentsId,
                                                          @Param("memberId") long memberId);
}
