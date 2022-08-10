package com.bucket.list.completedList.service;

import com.bucket.list.completedList.entity.Like;
import com.bucket.list.completedList.respository.LikeRepository;
import com.bucket.list.exception.BusinessLogicException;
import com.bucket.list.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeService {
  private final LikeRepository likeRepository;

  public Like createLike(Like like) {
    likeExists(like.getCompletedList().getCompletedListId(), like.getMember().getMemberId());
    return likeRepository.save(like);
  }

  public void deleteLike(Like like) {
    Like findLike = findVerifiedLike(like.getCompletedList().getCompletedListId(), like.getMember().getMemberId());
    likeRepository.delete(findLike);
  }

  public void likeExists(long completedListId, long memberId) {
    Optional<Like> optionalLike = likeRepository.findByCompletedListIdAndMemberId(completedListId, memberId);
    if(optionalLike.isPresent())
      throw new BusinessLogicException(ExceptionCode.LIKE_ALREADY_EXISTS);
  }

  public Like findVerifiedLike(long completedListId, long memberId) {
    Optional<Like> optionalLike = likeRepository.findByCompletedListIdAndMemberId(completedListId, memberId);
    return optionalLike.orElseThrow(() -> new BusinessLogicException(ExceptionCode.LIKE_NOT_FOUND));
  }
}
