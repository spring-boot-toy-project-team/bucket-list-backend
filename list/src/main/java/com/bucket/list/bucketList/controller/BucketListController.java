package com.bucket.list.bucketList.controller;

import com.bucket.list.bucketList.dto.BucketListRequestDto;
import com.bucket.list.bucketList.dto.BucketListResponseDto;
import com.bucket.list.bucketList.entity.BucketList;
import com.bucket.list.bucketList.mapper.BucketListMapper;
import com.bucket.list.bucketList.service.BucketListService;
import com.bucket.list.dto.MultiResponseWithPageInfoDto;
import com.bucket.list.dto.SingleResponseWithMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import javax.websocket.server.PathParam;
import java.util.List;


@RestController
@RequestMapping("/v1/bucket-groups/{group-id}/list")
@RequiredArgsConstructor
public class BucketListController {

    private final BucketListService bucketListService;
    private final BucketListMapper mapper;

    //버킷 등록
    @PostMapping
    public ResponseEntity createBucketList(@Positive @PathVariable("group-id") long groupId, @RequestBody BucketListRequestDto.CreateBucketListDto createBucketListDto){
        createBucketListDto.setGroupId(groupId);
        BucketList bucketList = bucketListService.createdBucketList(mapper.createBucketListDtoBucketList(createBucketListDto));
        return new ResponseEntity<>(new SingleResponseWithMessageDto<>(mapper.bucketListToBucketListInfo(bucketList), "SUCCESS"), HttpStatus.CREATED);

    }
    //버킷 조회
    @GetMapping("/{bucket-list-id}")
    public ResponseEntity getBucketList(@Positive @PathVariable("group-id") long groupId, @Positive @PathVariable("bucket-list-id") long bucketListId){
        BucketList bucketList = bucketListService.findBucketList(groupId, bucketListId);
        return new ResponseEntity<>(new SingleResponseWithMessageDto<>(mapper.bucketListToBucketListInfo(bucketList),"SUCCESS"),HttpStatus.OK);
    }
    //리스트들 조회
    @GetMapping
    public ResponseEntity getBucketLists(@Positive @PathVariable("group-id") long groupId, @Positive @PathParam("page") int page, @Positive @PathParam("size") int size){
        Page<BucketList> pageOfBucketLists = bucketListService.findBucketLists(groupId, page-1, size);
        List<BucketList> bucketLists = pageOfBucketLists.getContent();

        return new ResponseEntity<>(new MultiResponseWithPageInfoDto<>(mapper.bucketListsToGroupsInfo(bucketLists),pageOfBucketLists),HttpStatus.OK);
    }
    //리스트 변경
    @PatchMapping("/{bucket-list-id}")
    public ResponseEntity updateBucketList(@Positive @PathVariable("group-id") long groupId, @Positive @PathVariable("bucket-list-id") long bucketListId,
                                           @RequestBody BucketListRequestDto.UpdateBucketListDto updateBucketListDto){
        updateBucketListDto.setGroupId(groupId);
        updateBucketListDto.setBucketListId(bucketListId);
        BucketList bucketList = bucketListService.updateBucketList(mapper.updateBucketListDtoToBucketList(updateBucketListDto));
        return new ResponseEntity<>(new SingleResponseWithMessageDto<>(mapper.bucketListToBucketListInfo(bucketList),"SUCCESS"),HttpStatus.OK);
    }

    //리스트 삭제
    @DeleteMapping("/{bucket-list-id}")
    public ResponseEntity deleteBucketList(@Positive @PathVariable("group-id") long groupId, @Positive @PathVariable("bucket-list-id") long bucketListId){
        bucketListService.deleteBucketList(groupId, bucketListId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
