package com.bucket.list.completedList.mapper;

import com.bucket.list.bucketList.entity.BucketList;
import com.bucket.list.bucketList.entity.BucketListGroup;
import com.bucket.list.completedList.dto.CompletedListRequestDto;
import com.bucket.list.completedList.dto.CompletedListResponseDto;
import com.bucket.list.completedList.entity.CompletedList;
import com.bucket.list.member.entity.Member;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CompletedListMapper {
  default CompletedList createCompletedListDtoToCompletedList(CompletedListRequestDto.CreateCompletedListDto createCompletedListDto) {

    Member member = new Member();
    member.setMemberId(createCompletedListDto.getMemberId());
    BucketList bucketList = new BucketList();
    bucketList.setBucketListId(createCompletedListDto.getBucketListId());
    bucketList.setMember(member);
    CompletedList completedList = new CompletedList();
    completedList.setMember(member);
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
      .build();
  }

  default CompletedList updateCompletedListToCompletedList(CompletedListRequestDto.UpdateCompletedListDto updateCompletedListDto) {
    BucketList bucketList = new BucketList();
    Member member = new Member();
    member.setMemberId(updateCompletedListDto.getMemberId());
    bucketList.setMember(member);
    bucketList.setBucketListId(updateCompletedListDto.getBucketListId());
    CompletedList completedList = new CompletedList();
    completedList.setCompletedListId(updateCompletedListDto.getCompletedListId());
    completedList.setBucketList(bucketList);
    completedList.setContents(updateCompletedListDto.getContents());
    completedList.setTags(updateCompletedListDto.getTags());
    return completedList;
  }

//  default List<CompletedListResponseDto.CompletedListInfo> completeListsToCompletedInfoList(List<CompletedList> completedLists) {
//    return completedLists.stream()
//      .map(this::completeListToCompletedInfo)
//      .collect(Collectors.toList());
//  }
  List<CompletedListResponseDto.CompletedListInfo> completeListsToCompletedInfoList(List<CompletedList> completedLists);
}
