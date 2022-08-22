package com.bucket.list.stub.completedList;

import com.bucket.list.bucketList.entity.BucketList;
import com.bucket.list.completedList.dto.CompletedListRequestDto;
import com.bucket.list.completedList.dto.CompletedListResponseDto;
import com.bucket.list.completedList.entity.CompletedList;
import com.bucket.list.member.entity.Member;
import com.bucket.list.stub.bucektlist.BucketListStub;
import com.bucket.list.stub.member.MemberStub;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class CompletedListStub {
  private static final Member member = MemberStub.getMember();
  private static final BucketList bucketList = BucketListStub.getBucketList("target", 1L);

  public static CompletedListRequestDto.CreateCompletedListDto createCompletedListDto(String contents, String tags) {
    return CompletedListRequestDto.CreateCompletedListDto.builder()
      .bucketListId(bucketList.getBucketListId())
      .contents(contents)
      .tags(tags)
      .build();
  }

  public static CompletedList getCompletedList(String contents, String tags) {

    CompletedList completedList = new CompletedList();
    completedList.setCompletedListId(1L);
    completedList.setBucketList(bucketList);
    completedList.setMember(member);
    completedList.setImgs(new ArrayList<>());
    completedList.setTags(tags);
    completedList.setContents(contents);
    completedList.setCreatedAt(LocalDateTime.now());
    completedList.setModifiedAt(LocalDateTime.now());
    return completedList;
  }

  public static CompletedListResponseDto.CompletedListInfo getCompletedListInfo(CompletedList completedList) {
    return CompletedListResponseDto.CompletedListInfo.builder()
      .bucketListId(completedList.getBucketList().getBucketListId())
      .completedListId(completedList.getCompletedListId())
      .tags(completedList.getTags())
      .contents(completedList.getContents())
      .imgs(completedList.getImgs())
      .build();
  }
}
