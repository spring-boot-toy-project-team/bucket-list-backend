package com.bucket.list.completedList.service;

import com.bucket.list.bucketList.entity.BucketList;
import com.bucket.list.bucketList.service.BucketListService;
import com.bucket.list.completedList.entity.CompletedList;
import com.bucket.list.completedList.respository.CompletedListRepository;
import com.bucket.list.exception.BusinessLogicException;
import com.bucket.list.exception.ExceptionCode;
import com.bucket.list.util.tags.TagsHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
  private final BucketListService bucketListService;

  public CompletedList createCompletedList(CompletedList completedList, List<MultipartFile> files) {
    // TO-DO : 파일 로직 처리해서 CompletedList에 저장하기!
    BucketList bucketList = bucketListService.findVerifiedBucketListByIdAndMemberId(completedList.getBucketList().getBucketListId(),
      completedList.getMember().getMemberId());

    bucketListService.updateCompleted(bucketList, true);

    findIfExistsError(completedList.getBucketList().getBucketListId());
    return completedListRepository.save(completedList);
  }

  @Transactional(readOnly = true)
  public CompletedList findCompletedList(long bucketListId, long completedListId) {
    Optional<CompletedList> findCompletedList = completedListRepository.findByCompletedListIdAndBucketListId(completedListId,
      bucketListId);
    return findCompletedList.orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMPLETED_LIST_NOT_FOUND));
  }

  @Transactional(readOnly = true)
  public Page<CompletedList> findCompletedLists(long memberId, int page, int size) {
    return completedListRepository.findAllByMemberId(memberId,
      PageRequest.of(page, size, Sort.by("MODIFIED_AT").descending()));
  }

  public CompletedList updateCompletedList(CompletedList completedList, List<MultipartFile> files) {
    // TO-DO : 파일 변경 로직 수행
    CompletedList findCompletedList = findCompletedList(completedList.getBucketList().getBucketListId(),
      completedList.getCompletedListId());

    Optional.ofNullable(completedList.getTags())
      .ifPresent(tags -> findCompletedList.setTags(TagsHelper.duplicateCheck(tags)));
    Optional.ofNullable(completedList.getContents()).ifPresent(findCompletedList::setContents);
    return completedListRepository.save(findCompletedList);
  }

  public void deleteCompletedList(long bucketListId, long completedListId, long memberId) {
    // TO-DO : 바꾸기
    BucketList bucketList = bucketListService.findVerifiedBucketListByIdAndMemberId(bucketListId, memberId);
    bucketListService.updateCompleted(bucketList, false);
    CompletedList completedList = findCompletedList(bucketListId, completedListId);
    completedListRepository.delete(completedList);
  }

  @Transactional(readOnly = true)
  public void findIfExistsError(long bucketListId) {
    Optional<CompletedList> findCompletedList = completedListRepository.findByBucketListId(bucketListId);
    findCompletedList.ifPresent((exception)-> {
      throw new BusinessLogicException(ExceptionCode.COMPLETED_LIST_ALREADY_EXISTS);
    });
  }


  public Page<CompletedList> findCompletedListsByNickName(String nickName, int page, int size) {
    return completedListRepository.findAllByNickName(nickName,
      PageRequest.of(page, size, Sort.by("MODIFIED_AT").descending()));
  }

  public Page<CompletedList> findCompletedListsByTagName(String tagName, int page, int size) {
    return completedListRepository.findAllByTagName(tagName,
      PageRequest.of(page, size, Sort.by("MODIFIED_AT").descending()));
  }
}
