package com.bucket.list.bucketList.mapper;

import com.bucket.list.bucketList.dto.BucketListGroupRequestDto;
import com.bucket.list.bucketList.dto.BucketListGroupResponseDto;
import com.bucket.list.bucketList.dto.BucketListRequestDto;
import com.bucket.list.bucketList.dto.BucketListResponseDto;
import com.bucket.list.bucketList.entity.BucketList;
import com.bucket.list.bucketList.entity.BucketListGroup;
import com.bucket.list.member.entity.Member;
import org.mapstruct.Mapper;

import javax.validation.Valid;
import java.util.List;

@Mapper(componentModel = "spring")
public interface BucketListMapper {

    default BucketList createBucketListDtoBucketList(BucketListRequestDto.CreateBucketListDto createBucketListDto){
        BucketList bucketList = new BucketList();
        BucketListGroup bucketListGroup = new BucketListGroup();
        bucketListGroup.setBucketListGroupId(createBucketListDto.getGroupId());
        bucketList.setBucketListGroup(bucketListGroup);
        bucketList.setTarget(createBucketListDto.getTarget());
        return bucketList;
    }

    BucketListResponseDto.BucketListInfo bucketListToBucketListInfo(BucketList bucketList);

    List<BucketListResponseDto.BucketListInfo> bucketListsToGroupsInfo(List<BucketList> bucketLists);

    default BucketList updateBucketListDtoToBucketList(BucketListRequestDto.UpdateBucketListDto updateBucketListDto){
        BucketListGroup bucketListGroup = new BucketListGroup();
        bucketListGroup.setBucketListGroupId(updateBucketListDto.getGroupId());
        BucketList bucketList = new BucketList();
        bucketList.setBucketListId(updateBucketListDto.getBucketListId());
        bucketList.setTarget(updateBucketListDto.getTarget());
        bucketList.setBucketListGroup(bucketListGroup);
        return bucketList;
    }
}
