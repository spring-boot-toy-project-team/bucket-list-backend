package com.bucket.list.stub.bucketlist;

import com.bucket.list.bucketList.dto.BucketListRequestDto;
import com.bucket.list.bucketList.dto.BucketListResponseDto;
import com.bucket.list.bucketList.entity.BucketList;
import com.bucket.list.bucketList.entity.BucketListGroup;
import com.bucket.list.dto.response.MessageResponseDto;
import com.bucket.list.member.entity.Member;
import com.bucket.list.stub.member.MemberStub;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

public class BucketListStub {
    private static final Member member = MemberStub.getMember();
    private static final BucketListGroup bucketListGroup = BucketListGroupStub.getBucketGroup();

    public static BucketList getBucketList(){
        BucketList bucketList = new BucketList();
        bucketList.setBucketListId(1L);
        bucketList.setBucketListGroup(bucketListGroup);
        bucketList.setMember(member);
        bucketList.setTarget("자바");
        bucketList.setCompleted(false);
        return bucketList;
    }

    public static BucketList getBucketLists(String target, long bucketListId) {
        BucketList bucketList = new BucketList();
        bucketList.setBucketListId(bucketListId);
        bucketList.setBucketListGroup(bucketListGroup);
        bucketList.setMember(member);
        bucketList.setTarget(target);
        bucketList.setCompleted(false);
        return bucketList;
    }

    public static BucketListRequestDto.CreateBucketListDto createBucketListDto(){
        return BucketListRequestDto.CreateBucketListDto.builder()
                .memberId(member.getMemberId())
                .groupId(bucketListGroup.getBucketListGroupId())
                .target("자바")
                .build();
    }

    public static BucketListResponseDto.BucketListInfo bucketListInfo(){
        return BucketListResponseDto.BucketListInfo.builder()
                .bucketListId(getBucketList().getBucketListId())
                .target(getBucketList().getTarget())
                .completed(getBucketList().getCompleted())
                .build();
    }

    public static Page<BucketList> bucketListInfoPage(int page, int size){
        return new PageImpl<>(List.of(
                getBucketLists("자바", 1L),
                getBucketLists("파이썬", 2L),
                getBucketLists("Next.js", 3L)
        ), PageRequest.of(page - 1, size, Sort.by("BUCKET_LIST_ID").descending()), 3);
    }

    public static List<BucketListResponseDto.BucketListInfo> bucketListInfoList(int page, int size){
        return bucketListInfoPage(page,size).stream().map(bucketList ->
            BucketListResponseDto.BucketListInfo.builder()
                    .bucketListId(bucketList.getBucketListId())
                    .target(bucketList.getTarget())
                    .completed(bucketList.getCompleted())
                    .build()).collect(Collectors.toList()
        );

    }

    public static BucketListRequestDto.UpdateBucketListDto updateBucketListDto(BucketList bucketList){
        return BucketListRequestDto.UpdateBucketListDto.builder()
                .bucketListId(bucketList.getBucketListId())
                .memberId(member.getMemberId())
                .completed(bucketList.getCompleted())
                .target("제목 바꾼다")
                .groupId(bucketListGroup.getBucketListGroupId())
                .build();
    }

    public static BucketListResponseDto.BucketListInfo updateBucketListInfo(BucketList bucketList){
        return BucketListResponseDto.BucketListInfo.builder()
                .bucketListId(bucketList.getBucketListId())
                .target("제목 바꾼다")
                .completed(bucketList.getCompleted())
                .build();
    }

    public static MessageResponseDto getBucketListResult(){
        return MessageResponseDto.builder()
                .message("SUCCESS")
                .build();
    }
    public static MessageResponseDto getBucketListCreateResult(){
        return MessageResponseDto.builder()
                .message("CREATED")
                .build();
    }
}
