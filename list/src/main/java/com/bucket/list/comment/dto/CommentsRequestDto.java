package com.bucket.list.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

public class CommentsRequestDto {
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class CreateCommentsDto {
    private long completedListId;
    @NotEmpty
    private String contents;
    private long memberId;
  }
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class UpdateCommentsDto{
    private long commentsId;
    private long completedListId;
    @NotEmpty
    private String contents;
    private long memberId;

  }
}
