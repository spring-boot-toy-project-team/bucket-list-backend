package com.bucket.list.bucketList.service;

import com.bucket.list.bucketList.entity.BucketList;
import com.bucket.list.bucketList.repository.BucketListRepository;
import com.bucket.list.exception.BusinessLogicException;
import com.bucket.list.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Positive;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BucketListService {
  private final BucketListRepository bucketListRepository;

  public BucketList createBucketList(BucketList bucketList) {
    return bucketListRepository.save(bucketList);
  }

  @Transactional(readOnly = true)
  public BucketList findBucketList(long groupId, long bucketListId, long memberId) {
    return findVerifiedBucketList(groupId, bucketListId, memberId);
  }

  @Transactional(readOnly = true)
  public Page<BucketList> findBucketLists(long groupId, int page, int size, long memberId) {
    return bucketListRepository.findAllByBucketListGroup(groupId,
      memberId, PageRequest.of(page, size, Sort.by("BUCKET_LIST_ID").descending()));
  }

  public void deleteBucketList(long groupId, long bucketListId, long memberId) {
    BucketList bucketList = findVerifiedBucketList(groupId, bucketListId, memberId);
    bucketListRepository.delete(bucketList);
  }

  public BucketList updateBucketList(BucketList bucketList) {
    BucketList findBucketList = findVerifiedBucketList(bucketList.getBucketListGroup().getBucketListGroupId(),
      bucketList.getBucketListId(), bucketList.getBucketListGroup().getMember().getMemberId());

    Optional.ofNullable(bucketList.getTarget()).ifPresent(findBucketList::setTarget);
    Optional.ofNullable(bucketList.getCompleted()).ifPresent(findBucketList::setCompleted);
    return bucketListRepository.save(findBucketList);
  }

  @Transactional(readOnly = true)
  public BucketList findVerifiedBucketList(long groupId, long bucketListId, long memberId) {
    Optional<BucketList> optionalBucketList = bucketListRepository.findByIdAndBucketListGroupIdAndMemberId(groupId, bucketListId, memberId);

    return optionalBucketList
      .orElseThrow(() -> new BusinessLogicException(ExceptionCode.BUCKET_LIST_NOT_FOUND));
  }

  public void updateCompleted(BucketList bucketList, boolean completed) {
    bucketList.setCompleted(completed);
    bucketListRepository.save(bucketList);
  }
}
