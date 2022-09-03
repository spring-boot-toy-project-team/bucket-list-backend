package com.bucket.list.stub.completedList;

import com.bucket.list.bucketList.entity.BucketList;
import com.bucket.list.completedList.dto.CompletedListRequestDto;
import com.bucket.list.completedList.dto.CompletedListResponseDto;
import com.bucket.list.completedList.entity.CompletedList;
import com.bucket.list.dto.response.MessageResponseDto;
import com.bucket.list.member.entity.Member;
import com.bucket.list.stub.bucketlist.BucketListStub;
import com.bucket.list.stub.member.MemberStub;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CompletedListStub {
    private static final Member member = MemberStub.getMember();
    private static final BucketList bucketList = BucketListStub.getBucketList();
    private static final BucketList bucketList2 = BucketListStub.getBucketList();
    private static final BucketList bucketList3 = BucketListStub.getBucketList();

    public static CompletedListRequestDto.CreateCompletedListDto createCompletedListDto() {
        return CompletedListRequestDto.CreateCompletedListDto.builder()
                .bucketListId(bucketList.getBucketListId())
                .contents(getCompletedList().getContents())
                .tags(getCompletedList().getTags())
                .build();
    }

    public static CompletedList getCompletedList() {
        CompletedList completedList = new CompletedList();
        completedList.setCompletedListId(1L);
        completedList.setBucketList(bucketList);
        completedList.setMember(member);
        completedList.setImgs(new ArrayList<>());
        completedList.setTags("#태그1#태그2");
        completedList.setContents("BucketList Finish");
        completedList.setCreatedAt(LocalDateTime.now());
        completedList.setModifiedAt(LocalDateTime.now());
        return completedList;
    }

    public static CompletedList getCompletedLists(String contents, String tags,long completedListId) {
        CompletedList completedList = new CompletedList();
        completedList.setCompletedListId(completedListId);
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

    public static Page<CompletedList> completedListPage(int page, int size){
        return new PageImpl<>(List.of(
                getCompletedLists("test1","#태그1#태그2",1L),
                getCompletedLists("test2","#태그3#태그4",2L),
                getCompletedLists("test3","#태그5#태그6",3L)
        ), PageRequest.of(page-1,size, Sort.by("COMPLETED_LIST_ID").descending()),3);
    }

    public static List<CompletedListResponseDto.CompletedListInfo> completedListInfoList(int page, int size){
        return completedListPage(page, size).stream().map(completedList -> CompletedListResponseDto.CompletedListInfo.builder()
                .completedListId(completedList.getCompletedListId())
                .contents(completedList.getContents())
                .bucketListId(bucketList.getBucketListId())
                .build()).collect(Collectors.toList());
    }

    public static CompletedListRequestDto.UpdateCompletedListDto updateCompletedListDto(CompletedList completedList){
        return CompletedListRequestDto.UpdateCompletedListDto.builder()
                .bucketListId(bucketList.getBucketListId())
                .completedListId(getCompletedList().getCompletedListId())
                .contents("test change")
                .tags("#태그3#태그4")
                .memberId(member.getMemberId())
                .build();
    }

    public static CompletedListResponseDto.CompletedListInfo updateResponseCompletedListDto(CompletedList completedList){
        return CompletedListResponseDto.CompletedListInfo.builder()
                .bucketListId(bucketList.getBucketListId())
                .completedListId(completedList.getCompletedListId())
                .contents("test change")
                .tags("#태그3#태그4")
                .build();
    }

    public static MessageResponseDto getCompletedListResult(){
        return MessageResponseDto.builder()
                .message("CREATED")
                .build();
    }


}