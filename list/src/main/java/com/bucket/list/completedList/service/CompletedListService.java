package com.bucket.list.completedList.service;

import com.bucket.list.bucketList.entity.BucketList;
import com.bucket.list.bucketList.service.BucketListService;
import com.bucket.list.completedList.entity.CompletedList;
import com.bucket.list.completedList.respository.CompletedListRepository;
import com.bucket.list.exception.BusinessLogicException;
import com.bucket.list.exception.ExceptionCode;
import com.bucket.list.tag.entity.CompletedListTag;
import com.bucket.list.tag.entity.Tag;
import com.bucket.list.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class CompletedListService {
  private final CompletedListRepository completedListRepository;
  private final TagService tagService;
  private final BucketListService bucketListService;

  public CompletedList createCompletedList(CompletedList completedList, List<MultipartFile> files) {
    // TO-DO : 파일 로직 처리해서 CompletedList에 저장하기!
    BucketList bucketList = bucketListService.findVerifiedBucketList(completedList.getBucketList()
        .getBucketListGroup().getBucketListGroupId(),
      completedList.getBucketList().getBucketListId());
    bucketList.setCompleted(true);
    bucketListService.updateBucketList(bucketList);

    return saveIfNotExists(completedList);
  }

  @Transactional(readOnly = true)
  public CompletedList findCompletedList(long bucketListId, long completedListId) {
    Optional<CompletedList> findCompletedList = completedListRepository.findByCompletedListIdAndBucketListId(completedListId,
      bucketListId);
    return findCompletedList.orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMPLETED_LIST_NOT_FOUND));
  }

  public CompletedList updateCompletedList(CompletedList completedList, List<MultipartFile> files) {
    // TO-DO : 파일 변경 로직 수행
    CompletedList findCompletedList = findCompletedList(completedList.getBucketList().getBucketListId(),
      completedList.getCompletedListId());
    // 태그 변경 필요
    // completedList의 스트림과 findCompletedList stream 둘을 비교해서 다르면 바꿔주기
    List<CompletedListTag> completedListTags = completedList.getCompletedListTags().stream()
      .peek(completedListTag -> {
        Tag createdTag = tagService.createTag(completedListTag.getTag());
        completedListTag.setTag(createdTag);
      }).collect(Collectors.toList());
    findCompletedList.setCompletedListTags(completedListTags);

    Optional.ofNullable(completedList.getContents()).ifPresent(findCompletedList::setContents);
    return completedListRepository.save(findCompletedList);
  }

  public void deleteCompletedList(long groupId, long bucketListId, long completedListId) {
    BucketList bucketList = bucketListService.findBucketList(groupId, bucketListId);
    bucketList.setCompleted(false);
    bucketListService.updateBucketList(bucketList);
    CompletedList completedList = findCompletedList(bucketListId, completedListId);
    completedListRepository.delete(completedList);
  }

  public CompletedList saveIfNotExists(CompletedList completedList) {
    List<CompletedListTag> completedListTags = completedList.getCompletedListTags().stream()
      .peek(completedListTag -> {
        Tag createdTag = tagService.createTag(completedListTag.getTag());
        completedListTag.setTag(createdTag);
      }).collect(Collectors.toList());
    completedList.setCompletedListTags(completedListTags);

    findIfExistsError(completedList.getBucketList().getBucketListId());
    return completedListRepository.save(completedList);
  }

  @Transactional(readOnly = true)
  public void findIfExistsError(long bucketListId) {
    Optional<CompletedList> findCompletedList = completedListRepository.findByBucketListId(bucketListId);
    findCompletedList.ifPresent((exception)-> {
      throw new BusinessLogicException(ExceptionCode.COMPLETED_LIST_ALREADY_EXISTS);
    });
  }


}
