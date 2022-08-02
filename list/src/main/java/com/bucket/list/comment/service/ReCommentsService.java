package com.bucket.list.comment.service;

import com.bucket.list.comment.entity.ReComments;
import com.bucket.list.comment.repository.ReCommentsRepository;
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
public class ReCommentsService {
  private final ReCommentsRepository reCommentsRepository;
  private final MemberService memberService;

  public ReComments createReComments(ReComments reComments, String email) {
    reComments.setMember(memberService.findVerifiedMemberByEmail(email));
    return reCommentsRepository.save(reComments);
  }

  @Transactional(readOnly = true)
  public Page<ReComments> getReComments(long commentsId, int page, int size) {
    return reCommentsRepository.findAllByCommentsId(commentsId,
      PageRequest.of(page, size, Sort.by("RE_COMMENTS_ID").descending()));
  }

  public ReComments updateReComments(ReComments reComments, String email) {
    Member member = memberService.findVerifiedMemberByEmail(email);
    ReComments findReComments
      = findVerifiedReComments(reComments.getComments().getCommentsId(), reComments.getReCommentsId(), member.getMemberId());
    Optional.ofNullable(reComments.getContents()).ifPresent(findReComments::setContents);
    return reCommentsRepository.save(findReComments);
  }

  public void deleteReComments(long commentsId, long reCommentsId, String email) {
    Member member = memberService.findVerifiedMemberByEmail(email);
    ReComments findReComments = findVerifiedReComments(commentsId, reCommentsId, member.getMemberId());
    reCommentsRepository.delete(findReComments);
  }

  @Transactional(readOnly = true)
  public ReComments findVerifiedReComments(long commentsId, long reCommentsId, long memberId) {
    Optional<ReComments> optionalReComments
      = reCommentsRepository.findByIdAndCommentsIdAndMemberId(commentsId, reCommentsId, memberId);
    return optionalReComments.orElseThrow(() -> new BusinessLogicException(ExceptionCode.RE_COMMENTS_IS_NOT_FOUND));
  }


}
