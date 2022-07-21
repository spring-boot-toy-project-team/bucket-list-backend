package com.bucket.list.bucketList.mapper;

import com.bucket.list.bucketList.dto.BucketListGroupRequestDto;
import com.bucket.list.bucketList.dto.BucketListGroupResponseDto;
import com.bucket.list.bucketList.entity.BucketListGroup;
import com.bucket.list.member.entity.Member;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BucketListGroupMapper {
  default BucketListGroup createGroupDtoToBucketListGroup(BucketListGroupRequestDto.CreateGroupDto groupDto) {
    BucketListGroup bucketListGroup = new BucketListGroup();
    Member member = new Member();
    member.setMemberId(groupDto.getMemberId());
    bucketListGroup.setTitle(groupDto.getTitle());
    bucketListGroup.setMember(member);
    return bucketListGroup;
  }

  BucketListGroupResponseDto.CreateGroupDto bucketListGroupToCreateGroupDto(BucketListGroup bucketListGroup);
  BucketListGroupResponseDto.GroupInfo bucketListGroupToGroupInfo(BucketListGroup bucketListGroup);

  List<BucketListGroupResponseDto.GroupInfo> bucketListGroupsToGroupInfo(List<BucketListGroup> bucketListGroups);

  BucketListGroup updateGroupDtoToBucketListGroup(BucketListGroupRequestDto.UpdateGroupDto updateGroupDto);
}
