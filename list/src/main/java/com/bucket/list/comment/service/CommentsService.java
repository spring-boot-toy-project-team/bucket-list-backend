package com.bucket.list.comment.service;

import com.bucket.list.bucketList.entity.BucketList;
import com.bucket.list.comment.entity.Comments;
import com.bucket.list.comment.repository.CommentsRepository;
import com.bucket.list.exception.BusinessLogicException;
import com.bucket.list.exception.ExceptionCode;
import com.bucket.list.member.entity.Member;
import com.bucket.list.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentsService {
  private final CommentsRepository commentsRepository;
  private final MemberService memberService;


  public Comments createComments(Comments comments) {
    return commentsRepository.save(comments);
  }

  @Transactional(readOnly = true)
  public Page<Comments> findCommentsLists(long completedListId, int page, int size) {
    return commentsRepository.findAllByCompletedListId(completedListId,
            PageRequest.of(page, size, Sort.by("COMMENTS_ID").descending()));
  }

  public Comments updateComments(Comments comments) {
    Comments findComments
            = findVerifiedComments(comments.getCommentsId(), comments.getCompletedList().getCompletedListId(), comments.getMember().getMemberId());

    Optional.ofNullable(comments.getContents()).ifPresent(findComments::setContents);
    return commentsRepository.save(findComments);
  }

  public void deleteComments(long commentsId, long completedListId, long memberId) {
    Comments findComments = findVerifiedComments(commentsId, completedListId, memberId);
    commentsRepository.delete(findComments);
  }

  @Transactional(readOnly = true)
  public Comments findVerifiedComments(long commentsId, long completedListId, long memberId) {
    Optional<Comments> optionalComments
            = commentsRepository.findByIdAndCompletedListIdAndMemberId(commentsId, completedListId, memberId);
    return optionalComments.orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENTS_IS_NOT_FOUND));
  }
}
