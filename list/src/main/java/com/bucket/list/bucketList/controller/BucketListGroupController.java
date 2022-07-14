package com.bucket.list.bucketList.controller;

import com.bucket.list.bucketList.dto.BucketListGroupRequestDto;
import com.bucket.list.bucketList.dto.BucketListGroupResponseDto;
import com.bucket.list.bucketList.entity.BucketListGroup;
import com.bucket.list.bucketList.mapper.BucketListGroupMapper;
import com.bucket.list.bucketList.service.BucketListGroupService;
import com.bucket.list.dto.MultiResponseWithPageInfoDto;
import com.bucket.list.dto.SingleResponseWithMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.websocket.server.PathParam;
import java.util.List;

@Validated
@RestController
@RequestMapping("/v1/bucket-groups")
@RequiredArgsConstructor
public class BucketListGroupController {
  private final BucketListGroupService bucketListGroupService;
  private final BucketListGroupMapper mapper;

  // 그룹 등록
  @PostMapping
  public ResponseEntity createGroup(@RequestBody @Valid BucketListGroupRequestDto.GroupDto groupDto) {
    BucketListGroup bucketListGroup = bucketListGroupService.createGroup(mapper.groupDtoToBucketListGroup(groupDto));
    return new ResponseEntity<>(new SingleResponseWithMessageDto<>(mapper.bucketListGroupToCreateGroupDto(bucketListGroup),
      "CREATED"), HttpStatus.CREATED);
  }

  // 그룹 조회
  @GetMapping("/{group-id}")
  public ResponseEntity getGroup(@Positive @PathVariable("group-id") long bucketListGroupId) {
    BucketListGroup bucketListGroup = bucketListGroupService.findBucketListGroup(bucketListGroupId);

    return new ResponseEntity<>(new SingleResponseWithMessageDto<>(mapper.bucketListGroupToGroupInfo(bucketListGroup),
      "SUCCESS"), HttpStatus.OK);
  }
  // 그룹들 조회
  @GetMapping
  public ResponseEntity getGroups(@Positive @PathParam("page") int page,
                                  @Positive @PathParam("size") int size) {
    Page<BucketListGroup> pageGroups = bucketListGroupService.findBucketListGroups(page - 1, size);
    List<BucketListGroup> bucketListGroups = pageGroups.getContent();

    return new ResponseEntity<>(new MultiResponseWithPageInfoDto<>(mapper.bucketListGroupsToGroupInfo(bucketListGroups),
      pageGroups),
      HttpStatus.OK);
  }
//  // 그룹 삭제
//  @DeleteMapping("/{group-id}")
//  public ResponseEntity deleteGroup(@Positive @PathVariable("group-id") long bucketListGroupId) {
//    return new ResponseEntity<>();
//  }
//  // 그룹 변경
//  @PatchMapping("/{group-id}")
//  public ResponseEntity updateGroup(@Positive @PathVariable("group-id") long bucketListGroupId) {
//    return new ResponseEntity<>();
//  }
}
