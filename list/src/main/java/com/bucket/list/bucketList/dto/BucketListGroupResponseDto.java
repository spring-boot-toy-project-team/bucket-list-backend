package com.bucket.list.bucketList.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class BucketListGroupResponseDto {
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class CreateGroupDto {
    private long bucketListGroupId;
    private String title;
    private int year;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class GroupInfo {
    private String title;
    private int year;
  }
}
