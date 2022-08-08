package com.bucket.list.completedList.dto;

import com.bucket.list.completedList.entity.Img;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class CompletedListResponseDto {
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class CompletedListInfo {
    private String contents;
    private long completedListId;
    private String tags;
    private List<Img> imgs;
    private long bucketListId;
  }
}
