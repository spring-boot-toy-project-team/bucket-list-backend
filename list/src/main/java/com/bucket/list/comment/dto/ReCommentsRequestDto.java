package com.bucket.list.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

public class ReCommentsRequestDto {
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class CreateReCommentsDto {
    private long commentsId;
    @NotEmpty
    private String contents;
    private long memberId;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class UpdateReCommentsDto {
    private long reCommentsId;
    private long commentsId;
    @NotEmpty
    private String contents;
    private long memberId;
  }
}
