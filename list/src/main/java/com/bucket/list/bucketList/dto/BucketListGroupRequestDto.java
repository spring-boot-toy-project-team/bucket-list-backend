package com.bucket.list.bucketList.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class BucketListGroupRequestDto {
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class CreateGroupDto {
    @NotNull
    private long memberId;
    @NotBlank
    private String title;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class UpdateGroupDto {
    private long bucketListGroupId;
    @NotNull
    private long memberId;
    @NotBlank
    private String title;
  }
}
