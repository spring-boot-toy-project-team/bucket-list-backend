package com.bucket.list.bucketList.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BucketListRequestDto {
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class CreateBucketListDto {
    private long groupId;

    @NotBlank
    private String target;
    private long memberId;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class UpdateBucketListDto {
    private long bucketListId;
    private long groupId;

    @NotBlank
    private String target;
    @NotNull
    private boolean completed;
    private long memberId;
  }
}
