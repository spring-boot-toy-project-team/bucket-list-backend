package com.bucket.list.comment.controller;

import com.bucket.list.auth.MemberDetails;
import com.bucket.list.bucketList.entity.BucketList;
import com.bucket.list.comment.dto.CommentsRequestDto;
import com.bucket.list.comment.entity.Comments;
import com.bucket.list.comment.mapper.CommentsMapper;
import com.bucket.list.comment.service.CommentsService;
import com.bucket.list.dto.response.MultiResponseWithPageInfoDto;
import com.bucket.list.dto.response.SingleResponseWithMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.websocket.server.PathParam;
import java.util.List;

@Validated
@RestController
@RequestMapping("/v1/complete/{completed-list-id}/comments")
@RequiredArgsConstructor
public class CommentController {
  private final CommentsService commentsService;
  private final CommentsMapper mapper;

  @PostMapping
  public ResponseEntity createComments(@AuthenticationPrincipal MemberDetails memberDetails,
                                       @Positive @PathVariable("completed-list-id") long completedListId,
                                       @RequestBody @Valid CommentsRequestDto.CreateCommentsDto createCommentsDto) {
    // TO-DO : MEMBER ID 이용하는 로직으로 변경
    System.out.println(memberDetails.getMemberId());
    createCommentsDto.setCompletedListId(completedListId);
    Comments comments = commentsService.createComments(mapper.createCommentsDtoToComments(createCommentsDto),
      memberDetails.getUsername());
    return new ResponseEntity<>(new SingleResponseWithMessageDto<>(mapper.commentsToCommentsInfo(comments),
      "SUCCESS"),
      HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity getComments(@Positive @PathVariable("completed-list-id") long completedListId,
                                    @Positive @PathParam("page") int page,
                                    @Positive @PathParam("size") int size) {
    Page<Comments> pageOfComments = commentsService.findCommentsLists(completedListId, page - 1, size);
    List<Comments> commentsList = pageOfComments.getContent();
    return new ResponseEntity<>(new MultiResponseWithPageInfoDto<>(mapper.commentsListToCommentsInfoList(commentsList),
      pageOfComments),
      HttpStatus.OK);
  }

  @PatchMapping("{comments-id}")
  public ResponseEntity updateComments(@AuthenticationPrincipal MemberDetails memberDetails,
                                       @Positive @PathVariable("completed-list-id") long completedListId,
                                       @Positive @PathVariable("comments-id") long commentsId,
                                       @RequestBody @Valid CommentsRequestDto.UpdateCommentsDto updateCommentsDto) {
    updateCommentsDto.setCommentsId(commentsId);
    updateCommentsDto.setCompletedListId(completedListId);
    Comments comments = commentsService.updateComments(mapper.updateCommentsDtoToComments(updateCommentsDto),
      memberDetails.getUsername());
    return new ResponseEntity<>(new SingleResponseWithMessageDto<>(mapper.commentsToCommentsInfo(comments),
      "SUCCESS"),
      HttpStatus.OK);
  }

  @DeleteMapping("{comments-id}")
  public ResponseEntity deleteComments(@AuthenticationPrincipal MemberDetails memberDetails,
                                       @Positive @PathVariable("completed-list-id") long completedListId,
                                       @Positive @PathVariable("comments-id") long commentsId) {
    commentsService.deleteComments(commentsId, completedListId, memberDetails.getEmail());
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
