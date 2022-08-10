package com.bucket.list.completedList.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


public class CompletedListRequestDto {
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class CreateCompletedListDto {
    private long bucketListId;
    @NotBlank
    private String contents;
    private String tags;
    private long memberId;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class UpdateCompletedListDto {
    private long completedListId;
    private long bucketListId;
    @NotBlank
    private String contents;
    private String tags;
    private long memberId;
  }
}
