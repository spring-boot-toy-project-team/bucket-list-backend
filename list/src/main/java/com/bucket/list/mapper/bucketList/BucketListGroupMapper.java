package com.bucket.list.mapper.bucketList;

import com.bucket.list.dto.bucketList.BucketListRequestDto;
import com.bucket.list.dto.bucketList.BucketListResponseDto;
import com.bucket.list.entity.bucketList.BucketListGroup;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BucketListGroupMapper {

    BucketListGroup createdBucketListGroupPost(BucketListRequestDto.createdBucketListGroup createdBucketListGroupDto);
    BucketListGroup updateBucketListGroupPost(BucketListRequestDto.updateBucketListGroup updateBucketListGroupDto);
    BucketListGroup findBucketListGroupPost(BucketListRequestDto.findBucketListGroup findBucketListGroupDto);
    BucketListGroup findBucketListsGroupPosts(BucketListRequestDto.findBucketListGroups findBucketListGroupsDto);
    BucketListGroup deleteBucketListGroupPost(BucketListRequestDto.deleteBucketListGroup deleteBucketListGroupDto);


    BucketListResponseDto.createdBucketListGroup createdBucketListGroupResponse(BucketListGroup bucketListGroup);
    BucketListResponseDto.updateBucketListGroup updateBucketListGroupResponse(BucketListGroup bucketListGroup);
    BucketListResponseDto.findBucketListGroup findBucketListGroupResponse(BucketListGroup bucketListGroup);
    BucketListResponseDto.findBucketListGroups findsBucketListGroupResponses(List<BucketListGroup> bucketListGroups);
    BucketListResponseDto.deleteBucketListGroup deleteBucketListGroupResponse(String message);
}
