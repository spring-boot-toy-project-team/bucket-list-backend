package com.bucket.list.bucketList.mapper;

import com.bucket.list.bucketList.dto.BucketListRequestDto;
import com.bucket.list.bucketList.dto.BucketListResponseDto;
import com.bucket.list.bucketList.entity.BucketList;
import com.bucket.list.bucketList.entity.BucketListGroup;
import com.bucket.list.member.entity.Member;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BucketListMapper {

  default BucketList createBucketListDtoToBucketList(BucketListRequestDto.CreateBucketListDto createBucketListDto) {
    BucketList bucketList = new BucketList();
    bucketList.setTarget(createBucketListDto.getTarget());
    BucketListGroup bucketListGroup = new BucketListGroup();
    Member member = new Member();
    member.setMemberId(createBucketListDto.getMemberId());
    bucketList.setMember(member);
    bucketListGroup.setMember(member);
    bucketListGroup.setBucketListGroupId(createBucketListDto.getGroupId());
    bucketList.setBucketListGroup(bucketListGroup);
    return bucketList;
  }
  default BucketListResponseDto.BucketListInfo bucketListToBucketListInfo(BucketList bucketList) {
    return BucketListResponseDto.BucketListInfo.builder()
            .bucketListId(bucketList.getBucketListId())
            .completed(bucketList.getCompleted())
            .target(bucketList.getTarget())
            .build();
  }
  List<BucketListResponseDto.BucketListInfo> bucketListsToBucketListInfo(List<BucketList> bucketLists);

  default BucketList updateBucketListDtoToBucketList(BucketListRequestDto.UpdateBucketListDto updateBucketListDto) {
    BucketListGroup bucketListGroup = new BucketListGroup();
    Member member = new Member();
    member.setMemberId(updateBucketListDto.getMemberId());
    bucketListGroup.setMember(member);
    bucketListGroup.setBucketListGroupId(updateBucketListDto.getGroupId());
    BucketList bucketList = new BucketList();
    bucketList.setBucketListId(updateBucketListDto.getBucketListId());
    bucketList.setTarget(updateBucketListDto.getTarget());
    bucketList.setBucketListGroup(bucketListGroup);
    return bucketList;
  }
}