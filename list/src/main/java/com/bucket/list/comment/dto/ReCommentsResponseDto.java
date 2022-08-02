package com.bucket.list.comment.dto;

import com.bucket.list.member.dto.MemberResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ReCommentsResponseDto {
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class ReCommentsInfo {
    private long reCommentsId;
    private MemberResponseDto.MemberComments memberComments;
    private String contents;
  }
}
