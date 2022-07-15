package com.bucket.list.bucketList.service;

import com.bucket.list.bucketList.entity.BucketListGroup;
import com.bucket.list.bucketList.repository.BucketListGroupRepository;
import com.bucket.list.exception.BusinessLogicException;
import com.bucket.list.exception.ExceptionCode;
import com.bucket.list.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class BucketListGroupService {

    private final BucketListGroupRepository bucketListGroupRepository;

    public BucketListGroupService(BucketListGroupRepository bucketListGroupRepository) {
        this.bucketListGroupRepository = bucketListGroupRepository;
    }

    public BucketListGroup createGroup(BucketListGroup bucketListGroup){
        return bucketListGroupRepository.save(bucketListGroup);
    }

    @Transactional(readOnly = true)
    public BucketListGroup findBucketListGroup(long bucketListGroupId){
        return findVerifiedGroup(bucketListGroupId);
    }

    @Transactional(readOnly = true)
    public Page<BucketListGroup> finBucketListGroups(int page,int size){
        return bucketListGroupRepository.findAll(PageRequest.of(page,size, Sort.by("bucketListGroupId").descending()));
    }

    public void deleteBucketListGroup(long bucketListGroupId){
        BucketListGroup bucketListGroup = findVerifiedGroup(bucketListGroupId);
        bucketListGroupRepository.delete(bucketListGroup);
    }

    public BucketListGroup updateGroup(BucketListGroup updateGroupDtoToBucketListGroup){
       BucketListGroup findBucketListGroup = findBucketListGroup(updateGroupDtoToBucketListGroup.getBucketListGroupId());
        Optional.ofNullable(updateGroupDtoToBucketListGroup.getTitle())
                .ifPresent(findBucketListGroup::setTitle);
        return findBucketListGroup;
    }

    @Transactional(readOnly = true)
    public BucketListGroup findVerifiedGroup(long bucketListGroupId) {
        Optional<BucketListGroup> optionalBucketListGroup = bucketListGroupRepository.findById(bucketListGroupId);

        BucketListGroup bucketListGroup = optionalBucketListGroup
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.BUCKET_LIST_GROUP_NOT_FOUND));

        return bucketListGroup;
    }


}
