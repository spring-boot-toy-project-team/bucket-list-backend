package com.bucket.list.bucketList.controller;

import com.bucket.list.auth.MemberDetails;
import com.bucket.list.bucketList.dto.BucketListRequestDto;
import com.bucket.list.bucketList.entity.BucketList;
import com.bucket.list.bucketList.mapper.BucketListMapper;
import com.bucket.list.bucketList.service.BucketListService;
import com.bucket.list.dto.response.MultiResponseWithPageInfoDto;
import com.bucket.list.dto.response.SingleResponseWithMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import javax.websocket.server.PathParam;
import java.util.List;

@Validated
@RestController
@RequestMapping("/v1/bucket-groups/{group-id}/list")
@RequiredArgsConstructor
public class BucketListController {
  private final BucketListService bucketListService;
  private final BucketListMapper mapper;

  // 버킷리스트 등록
  @PostMapping
  public ResponseEntity createBucketList(@AuthenticationPrincipal MemberDetails memberDetails,
                                         @Positive @PathVariable("group-id") long groupId,
                                         @RequestBody BucketListRequestDto.CreateBucketListDto createBucketListDto) {
    createBucketListDto.setMemberId(memberDetails.getMemberId());
    createBucketListDto.setGroupId(groupId);
    BucketList bucketList = bucketListService.createBucketList(mapper.createBucketListDtoToBucketList(createBucketListDto));

    return new ResponseEntity<>(new SingleResponseWithMessageDto<>(mapper.bucketListToBucketListInfo(bucketList),
      "SUCCESS"),
      HttpStatus.CREATED);
  }
  // 버킷리스트 조회
  @GetMapping("/{bucket-list-id}")
  public ResponseEntity getBucketList(@AuthenticationPrincipal MemberDetails memberDetails,
                                      @Positive @PathVariable("group-id") long groupId,
                                      @Positive @PathVariable("bucket-list-id") long bucketListId) {
    BucketList bucketList = bucketListService.findBucketList(groupId, bucketListId, memberDetails.getMemberId());
    return new ResponseEntity<>(new SingleResponseWithMessageDto<>(mapper.bucketListToBucketListInfo(bucketList),
      "SUCCESS"),
      HttpStatus.OK);
  }

  // 버킷 리스트들 조회
  @GetMapping
  public ResponseEntity getBucketLists(@AuthenticationPrincipal MemberDetails memberDetails,
                                       @Positive @PathVariable("group-id") long groupId,
                                       @Positive @PathParam("page") int page,
                                       @Positive @PathParam("size") int size) {
    Page<BucketList> pageOfBucketLists = bucketListService.findBucketLists(groupId, page - 1, size, memberDetails.getMemberId());
    List<BucketList> bucketLists = pageOfBucketLists.getContent();

    return new ResponseEntity<>(new MultiResponseWithPageInfoDto<>(mapper.bucketListsToBucketListInfo(bucketLists),
      pageOfBucketLists),
      HttpStatus.OK);
  }

  // 버킷 리스트 삭제
  @DeleteMapping("{bucket-list-id}")
  public ResponseEntity deleteBucketList(@AuthenticationPrincipal MemberDetails memberDetails,
                                         @Positive @PathVariable("group-id") long groupId,
                                         @Positive @PathVariable("bucket-list-id") long bucketListId) {
    bucketListService.deleteBucketList(groupId, bucketListId, memberDetails.getMemberId());
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  // 버킷 리스트 변경
  @PatchMapping("{bucket-list-id}")
  public ResponseEntity updateBucketList(@AuthenticationPrincipal MemberDetails memberDetails,
                                         @Positive @PathVariable("group-id") long groupId,
                                         @Positive @PathVariable("bucket-list-id") long bucketListId,
                                         @RequestBody BucketListRequestDto.UpdateBucketListDto updateBucketListDto) {
    updateBucketListDto.setMemberId(memberDetails.getMemberId());
    updateBucketListDto.setGroupId(groupId);
    updateBucketListDto.setBucketListId(bucketListId);
    BucketList bucketList = bucketListService.updateBucketList(mapper.updateBucketListDtoToBucketList(updateBucketListDto));

    return new ResponseEntity<>(new SingleResponseWithMessageDto(mapper.bucketListToBucketListInfo(bucketList),
      "SUCCESS"),
      HttpStatus.OK);
  }
}
