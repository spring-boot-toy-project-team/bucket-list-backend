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

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BucketListService {

    private final BucketListRepository bucketListRepository;


    //버킷리스트 생성
    public BucketList createdBucketList(BucketList bucketList){
        return bucketListRepository.save(bucketList);
    }

    //조회
    @Transactional
    public BucketList findBucketList(long groupId, long bucketListId){
        return findVerifiedBucketList(groupId,bucketListId);
    }

    //리스트들
    @Transactional(readOnly = true)
    public Page<BucketList> findBucketLists(long groupId, int page, int size){
        return bucketListRepository.findAllByBucketListGroupId(groupId, PageRequest.of(page,size, Sort.by("groupId").descending()));
    }

    //삭제
    @Transactional
    public void deleteBucketList(long groupId, long bucketListId){
        BucketList bucketList = findVerifiedBucketList(groupId, bucketListId);
        bucketListRepository.delete(bucketList);
    }

    //변경
    @Transactional
    public BucketList updateBucketList(BucketList bucketList){
        BucketList findBucketList = findVerifiedBucketList(bucketList.getBucketListGroup().getBucketListGroupId(), bucketList.getBucketListId());

        Optional.ofNullable(bucketList.getTarget()).ifPresent(findBucketList::setTarget);
        updateCompleted(bucketList, bucketList.getCompleted());
        return bucketListRepository.save(findBucketList);
    }

    @Transactional(readOnly = true)
    public BucketList findVerifiedBucketList(long groupId, long bucketListId) {
        Optional<BucketList> optionalBucketList = bucketListRepository.findByBucketListGroupIdAndId(groupId, bucketListId);

        BucketList bucketList = optionalBucketList
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.BUCKET_LIST_NOT_FOUND));

        return bucketList;
    }

    public void updateCompleted(BucketList bucketList,boolean completed){
        bucketList.setCompleted(completed);
        bucketListRepository.save(bucketList);
    }
}
