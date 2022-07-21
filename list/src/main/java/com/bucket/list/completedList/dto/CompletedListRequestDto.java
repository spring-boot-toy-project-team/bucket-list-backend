package com.bucket.list.completedList.dto;

import com.bucket.list.tag.dto.CompletedListTagRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class CompletedListRequestDto {
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class CreateCompletedListDto {
    private long groupId;
    private long bucketListId;
    @NotBlank
    private String contents;
    private List<CompletedListTagRequestDto.CreateCompletedTagDto> completedListTags;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class UpdateCompletedListDto {
    private long completedListId;
    private long groupId;
    private long bucketListId;
    @NotBlank
    private String contents;
    private List<CompletedListTagRequestDto.CreateCompletedTagDto> completedListTags;
  }
}
