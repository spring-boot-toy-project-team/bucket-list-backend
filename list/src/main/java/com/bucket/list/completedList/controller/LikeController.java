package com.bucket.list.completedList.controller;

import com.bucket.list.auth.MemberDetails;
import com.bucket.list.completedList.entity.Like;
import com.bucket.list.completedList.mapper.LikeMapper;
import com.bucket.list.completedList.service.LikeService;
import com.bucket.list.dto.response.SingleResponseWithMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Valid
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/complete/like/{completed-list-id}")
public class LikeController {
    private final LikeService likeService;
    private final LikeMapper mapper;

    @PostMapping
    public ResponseEntity createLike(@AuthenticationPrincipal MemberDetails memberDetails,
                                     @Positive @PathVariable("completed-list-id") long completedListId){
        Like like = likeService.createLike(mapper.createLike(completedListId, memberDetails.getMemberId()));
        return new ResponseEntity<>(new SingleResponseWithMessageDto<>(mapper.likeToLikeInfo(like), "SUCCESS"), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity deleteLike(@AuthenticationPrincipal MemberDetails memberDetails,
                                     @Positive @PathVariable("completed-list-id") long completedListId){
        likeService.deleteLike(mapper.createLike(completedListId, memberDetails.getMemberId()));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
