package com.bucket.list.comment.controller;

import com.bucket.list.comment.dto.CommentsRequestDto;
import com.bucket.list.comment.mapper.CommentsMapper;
import com.bucket.list.comment.service.CommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/v1/comments")
@RequiredArgsConstructor
public class CommentController {
  private final CommentsService commentsService;
  private final CommentsMapper mapper;

  @PostMapping
  public ResponseEntity createComments(CommentsRequestDto.CreateCommentsDto createCommentsDto) {
    return new ResponseEntity<>(HttpStatus.CREATED);
  }
}
