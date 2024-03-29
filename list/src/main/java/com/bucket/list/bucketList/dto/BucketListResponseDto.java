package com.bucket.list.bucketList.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class BucketListResponseDto {
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class BucketListInfo {
    private long bucketListId;
    private String target;
    private boolean completed;
  }
}
