package com.bucket.list.comment.controller;


import com.bucket.list.auth.MemberDetails;
import com.bucket.list.comment.dto.ReCommentsRequestDto;
import com.bucket.list.comment.entity.ReComments;
import com.bucket.list.comment.mapper.ReCommentsMapper;
import com.bucket.list.comment.service.ReCommentsService;
import com.bucket.list.dto.response.MultiResponseWithPageInfoDto;
import com.bucket.list.dto.response.SingleResponseWithMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.websocket.server.PathParam;
import java.util.List;

@Valid
@RestController
@RequestMapping("/v1/comments/{comments-id}/re-comments")
@RequiredArgsConstructor
public class ReCommentsController {
    private final ReCommentsService reCommentsService;
    private final ReCommentsMapper mapper;

    @PostMapping
    public ResponseEntity createReComments(@AuthenticationPrincipal MemberDetails memberDetails,
                                           @Positive @PathVariable("comments-id") long commentsId,
                                           @RequestBody @Valid ReCommentsRequestDto.CreateReCommentsDto createReCommentsDto) {
        createReCommentsDto.setCommentsId(commentsId);
        createReCommentsDto.setMemberId(memberDetails.getMemberId());
        ReComments reComments
                = reCommentsService.createReComments(mapper.createReCommentsDtoToReComments(createReCommentsDto));
        return new ResponseEntity<>(new SingleResponseWithMessageDto<>(mapper.reCommentsToReCommentsInfo(reComments),
                "SUCCESS"),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity getReComments(@Positive @PathVariable("comments-id") long commentsId,
                                        @Positive @PathParam("page") int page,
                                        @Positive @PathParam("size") int size) {
        Page<ReComments> pageOfReComments = reCommentsService.getReComments(commentsId, page - 1, size);
        List<ReComments> reCommentsList = pageOfReComments.getContent();

        return new ResponseEntity<>(new MultiResponseWithPageInfoDto<>(mapper.reCommentsListToReCommentsInfoList(reCommentsList),
                pageOfReComments),
                HttpStatus.OK);
    }

    @PatchMapping("/{re-comments-id}")
    public ResponseEntity updateReComments(@AuthenticationPrincipal MemberDetails memberDetails,
                                           @Positive @PathVariable("comments-id") long commentsId,
                                           @Positive @PathVariable("re-comments-id") long reCommentsId,
                                           @RequestBody @Valid ReCommentsRequestDto.UpdateReCommentsDto updateReCommentsDto) {
        updateReCommentsDto.setCommentsId(commentsId);
        updateReCommentsDto.setReCommentsId(reCommentsId);
        updateReCommentsDto.setMemberId(memberDetails.getMemberId());
        ReComments reComments
                = reCommentsService.updateReComments(mapper.updateReCommentsDtoToReComments(updateReCommentsDto));
        return new ResponseEntity<>(new SingleResponseWithMessageDto<>(mapper.reCommentsToReCommentsInfo(reComments),
                "SUCCESS"),
                HttpStatus.OK);
    }

    @DeleteMapping("/{re-comments-id}")
    public ResponseEntity deleteReComments(@AuthenticationPrincipal MemberDetails memberDetails,
                                           @Positive @PathVariable("comments-id") long commentsId,
                                           @Positive @PathVariable("re-comments-id") long reCommentsId) {
        reCommentsService.deleteReComments(commentsId, reCommentsId, memberDetails.getMemberId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}