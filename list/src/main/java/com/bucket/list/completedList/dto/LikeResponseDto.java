package com.bucket.list.completedList.dto;

import lombok.*;

public class LikeResponseDto {
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class LikeInfo {
    private long memberId;
    private long completedListId;
  }
}
