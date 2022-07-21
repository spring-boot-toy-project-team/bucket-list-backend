package com.bucket.list.completList.mapper;

import com.bucket.list.bucketList.entity.BucketList;
import com.bucket.list.bucketList.entity.BucketListGroup;
import com.bucket.list.completList.dto.CompletedListRequestDto;
import com.bucket.list.completList.dto.CompletedListResponseDto;
import com.bucket.list.completList.entity.CompletedList;
import com.bucket.list.tag.dto.TagResponseDto;
import com.bucket.list.tag.entity.CompletedListTag;
import com.bucket.list.tag.entity.Tag;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.util.ClassUtil.name;

@Mapper(componentModel = "spring")
public interface CompletedListMapper {
    default CompletedList createCompletedListDtoToCompletedList(CompletedListRequestDto.CreateCompletedListDto createCompletedListDto) {
        BucketListGroup bucketListGroup = new BucketListGroup();
        bucketListGroup.setBucketListGroupId(createCompletedListDto.getGroupId());
        BucketList bucketList = new BucketList();
        bucketList.setBucketListId(createCompletedListDto.getBucketListId());
        bucketList.setBucketListGroup(bucketListGroup);
        CompletedList completedList = new CompletedList();
        completedList.setBucketList(bucketList);
        completedList.setContents(createCompletedListDto.getContents());
        List<CompletedListTag> completedListTags = createCompletedListDto.getCompletedListTags().stream().map(data -> {
            Tag tag = new Tag();
            tag.setName(data.getName());
            CompletedListTag completedListTag = new CompletedListTag();
            completedListTag.setTag(tag);
            completedListTag.setCompletedList(completedList);
            return completedListTag;
        }).collect(Collectors.toList());
        completedList.setCompletedListTags(completedListTags);
        return completedList;
    }

    default CompletedListResponseDto.CompletedListInfo completeListToCompletedInfo(CompletedList completedList) {
        return CompletedListResponseDto.CompletedListInfo.builder()
                .contents(completedList.getContents())
                .completedListId(completedList.getCompletedListId())
                .tags(completedList.getCompletedListTags().stream().map(completedListTag -> {
                            Tag tag = completedListTag.getTag();
                            return TagResponseDto.TagInfo.builder()
                                    .name(tag.getName())
                                    .tagId(tag.getTagId())
                                    .build();
                        })
                        .collect(Collectors.toList()))
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
        List<CompletedListTag> completedListTags = updateCompletedListDto.getCompletedListTags().stream().map(data -> {
            Tag tag = new Tag();
            tag.setName(data.getName());
            CompletedListTag completedListTag = new CompletedListTag();
            completedListTag.setTag(tag);
            completedListTag.setCompletedList(completedList);
            return completedListTag;
        }).collect(Collectors.toList());
        completedList.setCompletedListTags(completedListTags);
        return completedList;
    }
}