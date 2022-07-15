package com.bucket.list.bucketList.service;

import com.bucket.list.bucketList.entity.BucketListGroup;
import com.bucket.list.bucketList.repository.BucketListGroupRepository;
import com.bucket.list.exception.BusinessLogicException;
import com.bucket.list.exception.ExceptionCode;
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
public class BucketListGroupService {
  private final BucketListGroupRepository bucketListGroupRepository;

  public BucketListGroup createGroup(BucketListGroup bucketListGroup) {
    return bucketListGroupRepository.save(bucketListGroup);
  }

  @Transactional(readOnly = true)
  public BucketListGroup findBucketListGroup(long bucketListGroupId) {
    // TO-DO : memberId에 해당하는 BucketListGroup 조회 로직 추가
    return findVerifiedGroup(bucketListGroupId);
  }

  public Page<BucketListGroup> findBucketListGroups(int page, int size) {
    return bucketListGroupRepository.findAll(PageRequest.of(page, size,
      Sort.by("bucketListGroupId").descending()));
  }

  // TO-DO : memberId에 해당하고 BucketListGroupId가 일치하면 삭제하도록 설정 필요
  public void deleteBucketListGroup(long bucketListGroupId) {
     BucketListGroup bucketListGroup = findVerifiedGroup(bucketListGroupId);
     bucketListGroupRepository.delete(bucketListGroup);
  }

  // TO-DO : memberId에 해당하고 BucketListGroupId가 일치하면 변경 가능하도록 설정 필요
  public BucketListGroup updateGroup(BucketListGroup updateGroupDtoToBucketListGroup) {
    BucketListGroup findBucketListGroup = findBucketListGroup(updateGroupDtoToBucketListGroup.getBucketListGroupId());
    Optional.ofNullable(updateGroupDtoToBucketListGroup.getTitle())
      .ifPresent(findBucketListGroup::setTitle);

    return bucketListGroupRepository.save(findBucketListGroup);
  }

  // TO-DO : memberId에 해당하는 BucketListGroup 조회 로직 추가
  @Transactional(readOnly = true)
  public BucketListGroup findVerifiedGroup(long bucketListGroupId) {
    Optional<BucketListGroup> optionalBucketListGroup = bucketListGroupRepository.findById(bucketListGroupId);

    BucketListGroup bucketListGroup = optionalBucketListGroup
      .orElseThrow(() -> new BusinessLogicException(ExceptionCode.BUCKET_LIST_GROUP_NOT_FOUND));

    return bucketListGroup;
  }
}
