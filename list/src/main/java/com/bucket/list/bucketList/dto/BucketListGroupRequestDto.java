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
  public static class GroupDto {
    @NotNull
    private long memberId;
    @NotBlank
    private String title;
  }
}
