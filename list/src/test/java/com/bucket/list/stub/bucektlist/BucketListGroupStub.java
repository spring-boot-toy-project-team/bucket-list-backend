package com.bucket.list.stub.bucektlist;

import com.bucket.list.bucketList.dto.BucketListGroupRequestDto;
import com.bucket.list.bucketList.dto.BucketListGroupResponseDto;
import com.bucket.list.bucketList.entity.BucketListGroup;
import com.bucket.list.member.entity.Member;
import com.bucket.list.stub.member.MemberStub;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

public class BucketListGroupStub {
  private static Member member = MemberStub.getMember();
  public static BucketListGroupRequestDto.CreateGroupDto createBucketListGroupDto(String title) {
    return BucketListGroupRequestDto.CreateGroupDto.builder()
      .memberId(member.getMemberId())
      .title(title)
      .build();
  }

  public static BucketListGroup getBucketListGroup(String title, long bucketListGroupId) {
    BucketListGroup bucketListGroup = new BucketListGroup();
    bucketListGroup.setBucketListGroupId(bucketListGroupId);
    bucketListGroup.setMember(member);
    bucketListGroup.setTitle(title);
    bucketListGroup.setYear(2022);
    return bucketListGroup;
  }

  public static BucketListGroupResponseDto.CreateGroupDto getCreateBucketListGroupInfo(String title, long bucketListGroupId) {
    return BucketListGroupResponseDto.CreateGroupDto.builder()
      .bucketListGroupId(bucketListGroupId)
      .title(title)
      .year(2022)
      .build();
  }

  public static BucketListGroupResponseDto.GroupInfo getBucketListGroupInfo(String title) {
    return BucketListGroupResponseDto.GroupInfo.builder()
      .title(title)
      .year(2022)
      .build();
  }

  public static Page<BucketListGroup> getBucketListGroups(int page, int size) {
    return new PageImpl<>(List.of(
      getBucketListGroup("test1", 1L),
      getBucketListGroup("test2", 2L),
      getBucketListGroup("test3", 3L)
    ), PageRequest.of(page - 1, size, Sort.by("BUCKET_LIST_GROUP_ID").descending()), 3);
  }


  public static List<BucketListGroupResponseDto.GroupInfo> getBucketListGroupsResponse(int page, int size) {
    return getBucketListGroups(page, size).stream().map(bucketListGroup -> BucketListGroupResponseDto.GroupInfo.builder()
      .year(bucketListGroup.getYear())
      .title(bucketListGroup.getTitle())
      .build()).collect(Collectors.toList());
  }

  public static BucketListGroupRequestDto.UpdateGroupDto updateBucketListGroup(BucketListGroup bucketListGroup,
                                                                               String change) {
    return BucketListGroupRequestDto.UpdateGroupDto.builder()
      .bucketListGroupId(bucketListGroup.getBucketListGroupId())
      .memberId(bucketListGroup.getMember().getMemberId())
      .title(change)
      .build();
  }

  public static BucketListGroupResponseDto.GroupInfo getChangedBucketListGroup(BucketListGroup bucketListGroup, String change) {
    BucketListGroupRequestDto.UpdateGroupDto updateGroupDto = updateBucketListGroup(bucketListGroup, change);
    return BucketListGroupResponseDto.GroupInfo.builder()
      .title(updateGroupDto.getTitle())
      .year(2022)
      .build();
  }
}
