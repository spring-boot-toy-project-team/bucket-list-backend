package com.bucket.list.stub.bucketlist;

import com.bucket.list.bucketList.dto.BucketListGroupRequestDto;
import com.bucket.list.bucketList.dto.BucketListGroupResponseDto;
import com.bucket.list.bucketList.entity.BucketListGroup;
import com.bucket.list.dto.response.MessageResponseDto;
import com.bucket.list.member.entity.Member;
import com.bucket.list.stub.member.MemberStub;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class BucketListGroupStub {
    private static final Member member = MemberStub.getMember();

    public static BucketListGroup getBucketGroups(String title, long bucketListGroupId){
        BucketListGroup bucketListGroup = new BucketListGroup();
        bucketListGroup.setBucketListGroupId(bucketListGroupId);
        bucketListGroup.setMember(member);
        bucketListGroup.setTitle(title);
        bucketListGroup.setYear(2022);
        return bucketListGroup;
    }
    public static BucketListGroup getBucketGroup(){
        BucketListGroup bucketListGroup = new BucketListGroup();
        bucketListGroup.setBucketListGroupId(bucketListGroup.getBucketListGroupId());
        bucketListGroup.setMember(member);
        bucketListGroup.setTitle("test");
        bucketListGroup.setYear(2022);
        return bucketListGroup;
    }

//    public static BucketListGroupRequestDto.CreateGroupDto createGroupDto(String title){
//        return BucketListGroupRequestDto.CreateGroupDto.builder()
//                .memberId(member.getMemberId())
//                .title(title)
//                .build();
//    }
    public static BucketListGroupRequestDto.CreateGroupDto createGroupDto(){
        return BucketListGroupRequestDto.CreateGroupDto.builder()
                .memberId(member.getMemberId())
                .title("test")
                .build();
    }

//    public static BucketListGroupResponseDto.CreateGroupDto getCreateBucketGroupInfo(String title, long bucketListGroupId){
//        return BucketListGroupResponseDto.CreateGroupDto.builder()
//                .bucketListGroupId(bucketListGroupId)
//                .title(title)
//                .year(2002)
//                .build();
//    }
    public static BucketListGroupResponseDto.CreateGroupDto getCreateBucketGroupInfo(){
        return BucketListGroupResponseDto.CreateGroupDto.builder()
                .bucketListGroupId(getBucketGroup().getBucketListGroupId())
                .title("test")
                .year(2002)
                .build();
    }

    public static MessageResponseDto createResult(){
        return MessageResponseDto.builder()
                .message("CREATED")
                .build();
    }

    public static MessageResponseDto getBucketListGroupResult(){
        return MessageResponseDto.builder()
                .message("SUCCESS")
                .build();
    }

//    public static BucketListGroupResponseDto.GroupInfo getBucketGroupInfo(String title) {
//        return BucketListGroupResponseDto.GroupInfo.builder()
//                .title(title)
//                .year(2022)
//                .build();
//    }

    public static BucketListGroupResponseDto.GroupInfo getBucketGroupInfo() {
        return BucketListGroupResponseDto.GroupInfo.builder()
                .title("test")
                .year(2022)
                .build();
    }

    public static Page<BucketListGroup> getBucketListGroupPage(int page, int size){
        return new PageImpl<>(List.of(
                getBucketGroups("test1", 1L),
                getBucketGroups("test2", 2L),
                getBucketGroups("test3", 3L)

        ), PageRequest.of(page - 1, size, Sort.by("BUCKET_LIST_GROUP_ID").descending()), 3);
    }

    //질문
    public static List<BucketListGroupResponseDto.GroupInfo> groupBucketListGroupInfoList(int page, int size){
        return getBucketListGroupPage(page, size).stream().map(bucketListGroup -> {
            return BucketListGroupResponseDto.GroupInfo.builder()
                    .year(bucketListGroup.getYear())
                    .title(bucketListGroup.getTitle())
                    .build();
        }).collect(Collectors.toList());
    }

    public static BucketListGroupRequestDto.UpdateGroupDto updateBucketListGroupDto(BucketListGroup bucketListGroup){
        return BucketListGroupRequestDto.UpdateGroupDto.builder()
                .bucketListGroupId(1L)
                .memberId(member.getMemberId())
                .title("happy")
                .build();
    }

    public static BucketListGroupResponseDto.GroupInfo updateBucketListGroupInfo(BucketListGroup bucketListGroup){
        BucketListGroupRequestDto.UpdateGroupDto updateGroupDto = updateBucketListGroupDto(bucketListGroup);
        return BucketListGroupResponseDto.GroupInfo.builder()
                .title(updateGroupDto.getTitle())
                .year(2022)
                .build();
    }
}
