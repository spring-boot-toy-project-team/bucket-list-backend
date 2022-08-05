package com.bucket.list.completedList.mapper;

import com.bucket.list.bucketList.entity.BucketList;
import com.bucket.list.bucketList.entity.BucketListGroup;
import com.bucket.list.completedList.dto.CompletedListRequestDto;
import com.bucket.list.completedList.dto.CompletedListResponseDto;
import com.bucket.list.completedList.entity.CompletedList;
import com.bucket.list.member.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompletedListMapper {
  default CompletedList createCompletedListDtoToCompletedList(CompletedListRequestDto.CreateCompletedListDto createCompletedListDto) {

    BucketListGroup bucketListGroup = new BucketListGroup();
    bucketListGroup.setBucketListGroupId(createCompletedListDto.getGroupId());
    Member member = new Member();
    member.setMemberId(createCompletedListDto.getMemberId());
    bucketListGroup.setMember(member);
    BucketList bucketList = new BucketList();
    bucketList.setBucketListId(createCompletedListDto.getBucketListId());
    bucketList.setBucketListGroup(bucketListGroup);
    CompletedList completedList = new CompletedList();
    completedList.setBucketList(bucketList);
    completedList.setContents(createCompletedListDto.getContents());
    completedList.setTags(createCompletedListDto.getTags());
    return completedList;
  }

  default CompletedListResponseDto.CompletedListInfo completeListToCompletedInfo(CompletedList completedList) {
    return CompletedListResponseDto.CompletedListInfo.builder()
      .contents(completedList.getContents())
      .completedListId(completedList.getCompletedListId())
      .tags(completedList.getTags())
      .imgs(completedList.getImgs())
      .bucketListId(completedList.getBucketList().getBucketListId())
      .bucketListGroupId(completedList.getBucketList().getBucketListGroup().getBucketListGroupId())
      .build();
  }

  default CompletedList updateCompletedListToCompletedList(CompletedListRequestDto.UpdateCompletedListDto updateCompletedListDto) {
    BucketListGroup bucketListGroup = new BucketListGroup();
    bucketListGroup.setBucketListGroupId(updateCompletedListDto.getGroupId());
    BucketList bucketList = new BucketList();
    bucketList.setBucketListGroup(bucketListGroup);
    bucketList.setBucketListId(updateCompletedListDto.getBucketListId());
    CompletedList completedList = new CompletedList();
    completedList.setCompletedListId(updateCompletedListDto.getCompletedListId());
    completedList.setBucketList(bucketList);
    completedList.setContents(updateCompletedListDto.getContents());
    completedList.setTags(updateCompletedListDto.getTags());
    return completedList;
  }
}
