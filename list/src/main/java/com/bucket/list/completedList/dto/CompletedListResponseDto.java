package com.bucket.list.completedList.dto;

import com.bucket.list.completedList.entity.Img;
import com.bucket.list.tag.dto.CompletedListTagRequestDto;
import com.bucket.list.tag.dto.TagResponseDto;
import com.bucket.list.tag.entity.CompletedListTag;
import com.bucket.list.tag.entity.Tag;
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
    private List<TagResponseDto.TagInfo> tags;
    private List<Img> imgs;
    private long bucketListGroupId;
    private long bucketListId;
  }
}
