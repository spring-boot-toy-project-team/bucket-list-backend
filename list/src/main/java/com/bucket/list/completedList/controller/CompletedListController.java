package com.bucket.list.completedList.controller;

import com.bucket.list.auth.MemberDetails;
import com.bucket.list.completedList.dto.CompletedListRequestDto;
import com.bucket.list.completedList.entity.CompletedList;
import com.bucket.list.completedList.mapper.CompletedListMapper;
import com.bucket.list.completedList.service.CompletedListService;
import com.bucket.list.dto.response.MultiResponseWithPageInfoDto;
import com.bucket.list.dto.response.SingleResponseWithMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.websocket.server.PathParam;
import java.util.List;

@Validated
@RestController
@RequestMapping("/v1/bucket-list")
@RequiredArgsConstructor
public class CompletedListController {
  private final CompletedListService completedListService;
  private final CompletedListMapper mapper;

  // 완료된 버킷 리스트 등록
  @PostMapping(value = "/{bucket-list-id}/complete", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity createCompletedList(@AuthenticationPrincipal MemberDetails memberDetails,
                                            @Positive @PathVariable("bucket-list-id") long bucketListId,
                                            @Valid @RequestPart("data") CompletedListRequestDto.CreateCompletedListDto createCompletedListDto,
                                            @RequestPart(name = "files", required = false) List<MultipartFile> files) {
    createCompletedListDto.setBucketListId(bucketListId);
    createCompletedListDto.setMemberId(memberDetails.getMemberId());
    CompletedList completedList = completedListService.createCompletedList(mapper.createCompletedListDtoToCompletedList(createCompletedListDto), files);
    return new ResponseEntity<>(new SingleResponseWithMessageDto<>(mapper.completeListToCompletedInfo(completedList),
      "SUCCESS"),
      HttpStatus.CREATED);
  }

  // 완료된 버킷 리스트 조회
  @GetMapping("/{bucket-list-id}/complete/{completed-list-id}")
  public ResponseEntity getCompletedList(@AuthenticationPrincipal MemberDetails memberDetails,
                                         @Positive @PathVariable("bucket-list-id") long bucketListId,
                                         @Positive @PathVariable("completed-list-id") long completedListId) {
    CompletedList completedList = completedListService.findCompletedList(bucketListId, completedListId);
    return new ResponseEntity<>(new SingleResponseWithMessageDto<>(mapper.completeListToCompletedInfo(completedList),
      "SUCCESS"),
      HttpStatus.OK);
  }

  // 완료된 버킷 리스트들 조회
  @GetMapping("/complete")
  public ResponseEntity getCompletedLists(@AuthenticationPrincipal MemberDetails memberDetails,
                                          @Positive @PathParam("page") int page,
                                          @Positive @PathParam("size") int size) {
    Page<CompletedList> pageOfCompletedList
      = completedListService.findCompletedLists(memberDetails.getMemberId(), page - 1, size);
    List<CompletedList> completedLists = pageOfCompletedList.getContent();
    return new ResponseEntity<>(new MultiResponseWithPageInfoDto<>(mapper.completeListsToCompletedInfoList(completedLists),
      pageOfCompletedList),
      HttpStatus.OK);
  }

  // 완료된 버킷 리스트 변경
  @PatchMapping("/{bucket-list-id}/complete/{completed-list-id}")
  public ResponseEntity updateCompletedList(@AuthenticationPrincipal MemberDetails memberDetails,
                                            @Positive @PathVariable("bucket-list-id") long bucketListId,
                                            @Positive @PathVariable("completed-list-id") long completedListId,
                                            @Valid @RequestPart("data") CompletedListRequestDto.UpdateCompletedListDto updateCompletedListDto,
                                            @RequestPart(name = "files", required = false) List<MultipartFile> files) {
    updateCompletedListDto.setCompletedListId(completedListId);
    updateCompletedListDto.setBucketListId(bucketListId);
    updateCompletedListDto.setMemberId(memberDetails.getMemberId());
    CompletedList completedList = completedListService.updateCompletedList(
      mapper.updateCompletedListToCompletedList(updateCompletedListDto),
      files);

    return new ResponseEntity<>(new SingleResponseWithMessageDto<>(mapper.completeListToCompletedInfo(completedList),
      "SUCCESS"),
      HttpStatus.OK);
  }


  // 완료된 버킷 리스트 삭제
  @DeleteMapping("/{bucket-list-id}/complete/{completed-list-id}")
  public ResponseEntity deleteCompletedList(@AuthenticationPrincipal MemberDetails memberDetails,
                                            @Positive @PathVariable("bucket-list-id") long bucketListId,
                                            @Positive @PathVariable("completed-list-id") long completedListId) {
    completedListService.deleteCompletedList(bucketListId, completedListId, memberDetails.getMemberId());
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("/complete/nick-name")
  public ResponseEntity getCompletedListByNickName(@PathParam("nickName") String nickName,
                                                   @Positive @PathParam("page") int page,
                                                   @Positive @PathParam("size") int size) {
    Page<CompletedList> pageOfCompletedList = completedListService.findCompletedListsByNickName(nickName, page - 1, size);
    List<CompletedList> completedLists = pageOfCompletedList.getContent();
    return new ResponseEntity<>(new MultiResponseWithPageInfoDto<>(mapper.completeListsToCompletedInfoList(completedLists),
      pageOfCompletedList),
      HttpStatus.OK);
  }

  @GetMapping("/complete/tag-name")
  public ResponseEntity getCompletedListByTagName(@PathParam("tagName") String tagName,
                                                  @Positive @PathParam("page") int page,
                                                  @Positive @PathParam("size") int size) {
    Page<CompletedList> pageOfCompletedList = completedListService.findCompletedListsByTagName(tagName,page-1,size);
    List<CompletedList> completedLists = pageOfCompletedList.getContent();

    return new ResponseEntity<>(new MultiResponseWithPageInfoDto<>(mapper.completeListsToCompletedInfoList(completedLists),
      pageOfCompletedList),
      HttpStatus.OK);
  }
}
