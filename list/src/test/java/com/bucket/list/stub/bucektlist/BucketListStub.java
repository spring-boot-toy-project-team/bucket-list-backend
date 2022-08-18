package com.bucket.list.stub.bucektlist;

import com.bucket.list.bucketList.dto.BucketListGroupRequestDto;
import com.bucket.list.bucketList.dto.BucketListRequestDto;
import com.bucket.list.bucketList.dto.BucketListResponseDto;
import com.bucket.list.bucketList.entity.BucketList;
import com.bucket.list.bucketList.entity.BucketListGroup;
import com.bucket.list.member.entity.Member;
import com.bucket.list.stub.member.MemberStub;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

public class BucketListStub {
  private static Member member = MemberStub.getMember();
  private static BucketListGroup bucketListGroup = BucketListGroupStub.getBucketListGroup("test", 1L);

  public static BucketList getBucketList(String target, long bucketListId) {
    BucketList bucketList = new BucketList();
    bucketList.setBucketListId(bucketListId);
    bucketList.setBucketListGroup(bucketListGroup);
    bucketList.setMember(member);
    bucketList.setTarget(target);
    bucketList.setCompleted(false);
    return bucketList;
  }
  public static BucketListRequestDto.CreateBucketListDto createBucketListDto(String target) {
    return BucketListRequestDto.CreateBucketListDto.builder()
      .memberId(member.getMemberId())
      .groupId(bucketListGroup.getBucketListGroupId())
      .target(target)
      .build();
  }

  public static BucketListResponseDto.BucketListInfo getBucketListInfo(BucketList bucketList) {
    return BucketListResponseDto.BucketListInfo.builder()
      .bucketListId(bucketList.getBucketListId())
      .target(bucketList.getTarget())
      .completed(bucketList.getCompleted())
      .build();
  }

  public static Page<BucketList> getBucketLists(int page, int size) {
    return new PageImpl<>(List.of(
      getBucketList("test1", 1L),
      getBucketList("test2", 2L),
      getBucketList("test3", 3L)
    ), PageRequest.of(page - 1, size, Sort.by("BUCKET_LIST_ID").descending()), 3);
  }

  public static List<BucketListResponseDto.BucketListInfo> getBucketListsResponse(int page, int size) {
    return getBucketLists(page, size).stream()
      .map(bucketList -> BucketListResponseDto.BucketListInfo.builder()
        .bucketListId(bucketList.getBucketListId())
        .completed(bucketList.getCompleted())
        .target(bucketList.getTarget())
        .build())
      .collect(Collectors.toList());
  }

  public static BucketListRequestDto.UpdateBucketListDto updateBucketListDto(BucketList bucketList, String change) {
    return BucketListRequestDto.UpdateBucketListDto.builder()
      .bucketListId(bucketList.getBucketListId())
      .memberId(bucketList.getMember().getMemberId())
      .target(change)
      .groupId(bucketList.getBucketListGroup().getBucketListGroupId())
      .completed(bucketList.getCompleted())
      .build();
  }

  public static BucketListResponseDto.BucketListInfo getChangedBucketList(BucketList bucketList, String change) {
    return BucketListResponseDto.BucketListInfo.builder()
      .bucketListId(bucketList.getBucketListId())
      .target(change)
      .completed(bucketList.getCompleted())
      .build();
  }
}
