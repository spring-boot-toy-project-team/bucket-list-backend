package com.bucket.list.completList.service;

import com.bucket.list.bucketList.entity.BucketList;
import com.bucket.list.bucketList.service.BucketListService;
import com.bucket.list.completList.entity.CompletedList;
import com.bucket.list.completList.repository.CompletedListRepository;
import com.bucket.list.exception.BusinessLogicException;
import com.bucket.list.exception.ExceptionCode;
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
    private final BucketListService bucketListService;

    public CompletedList createCompletedList(CompletedList completedList, List<MultipartFile> files) {
        //1.save
        //2.파일 로직 처리
        //3.태그중복체크
        //.파일경로저장
        //1. 존재하면 아이디값까지 넣어준다
        //2. 태그값들을 먼저 저장하면서 태그 업데이트
        BucketList bucketList = bucketListService.findVerifiedBucketList(completedList.getBucketList()
                        .getBucketListGroup().getBucketListGroupId(),
                completedList.getBucketList().getBucketListId());
        bucketList.setCompleted(true);
        bucketListService.updateBucketList(bucketList);

        findIfExistsError(completedList.getBucketList().getBucketListId());
        return completedListRepository.save(completedList);

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

        Optional.ofNullable(completedList.getTags()).ifPresent(tags -> findCompletedList.getTags());

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


    @Transactional(readOnly = true)
    public void findIfExistsError(long bucketListId) {
        Optional<CompletedList> findCompletedList = completedListRepository.findByBucketListId(bucketListId);
        findCompletedList.ifPresent((exception)-> {
            throw new BusinessLogicException(ExceptionCode.COMPLETED_LIST_ALREADY_EXISTS);
        });
    }



}
