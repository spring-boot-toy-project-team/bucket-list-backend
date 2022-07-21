package com.bucket.list.completedList.controller;

import com.bucket.list.completedList.dto.CompletedListRequestDto;
import com.bucket.list.completedList.dto.CompletedListResponseDto;
import com.bucket.list.completedList.entity.CompletedList;
import com.bucket.list.completedList.mapper.CompletedListMapper;
import com.bucket.list.completedList.service.CompletedListService;
import com.bucket.list.dto.SingleResponseWithMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Validated
@RestController
@RequestMapping("/v1/bucket-groups/{group-id}/list/{bucket-list-id}/complete")
@RequiredArgsConstructor
public class CompletedListController {
  private final CompletedListService completedListService;
  private final CompletedListMapper mapper;

  // 완료된 버킷 리스트 등록
  @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity createCompletedList(@Positive @PathVariable("group-id") long groupId,
                                            @Positive @PathVariable("bucket-list-id") long bucketListId,
                                            @Valid @RequestPart("data") CompletedListRequestDto.CreateCompletedListDto createCompletedListDto,
                                            @RequestPart(name = "files", required = false) List<MultipartFile> files) {
    createCompletedListDto.setGroupId(groupId);
    createCompletedListDto.setBucketListId(bucketListId);
    CompletedList completedList = completedListService.createCompletedList(mapper.createCompletedListDtoToCompletedList(createCompletedListDto), files);
    return new ResponseEntity<>(new SingleResponseWithMessageDto<>(mapper.completeListToCompletedInfo(completedList),
      "SUCCESS"),
      HttpStatus.CREATED);
  }

  // 완료된 버킷 리스트 조회
  @GetMapping("/{completed-list-id}")
  public ResponseEntity getCompletedList(@Positive @PathVariable("group-id") long groupId,
                                         @Positive @PathVariable("bucket-list-id") long bucketListId,
                                         @Positive @PathVariable("completed-list-id") long completedListId) {
    CompletedList completedList = completedListService.findCompletedList(bucketListId, completedListId);
    return new ResponseEntity<>(new SingleResponseWithMessageDto<>(mapper.completeListToCompletedInfo(completedList),
      "SUCCESS"),
      HttpStatus.OK);
  }

  // 완료된 버킷 리스트 변경
  @PatchMapping("/{completed-list-id}")
  public ResponseEntity updateCompletedList(@Positive @PathVariable("group-id") long groupId,
                                            @Positive @PathVariable("bucket-list-id") long bucketListId,
                                            @Positive @PathVariable("completed-list-id") long completedListId,
                                            @Valid @RequestPart("data") CompletedListRequestDto.UpdateCompletedListDto updateCompletedListDto,
                                            @RequestPart(name = "files", required = false) List<MultipartFile> files) {
    updateCompletedListDto.setCompletedListId(completedListId);
    updateCompletedListDto.setBucketListId(bucketListId);
    updateCompletedListDto.setGroupId(groupId);
    CompletedList completedList = completedListService.updateCompletedList(
      mapper.updateCompletedListToCompletedList(updateCompletedListDto),
      files);

    return new ResponseEntity<>(new SingleResponseWithMessageDto<>(mapper.completeListToCompletedInfo(completedList),
      "SUCCESS"),
      HttpStatus.OK);
  }


  // 완료된 버킷 리스트 삭제
  @DeleteMapping("/{completed-list-id}")
  public ResponseEntity deleteCompletedList(@Positive @PathVariable("group-id") long groupId,
                                         @Positive @PathVariable("bucket-list-id") long bucketListId,
                                         @Positive @PathVariable("completed-list-id") long completedListId) {
    completedListService.deleteCompletedList(groupId, bucketListId, completedListId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
