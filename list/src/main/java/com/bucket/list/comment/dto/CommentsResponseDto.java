package com.bucket.list.comment.dto;

import com.bucket.list.member.dto.MemberResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CommentsResponseDto {
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class CommentsInfo {
    private long commentsId;
    private MemberResponseDto.MemberComments memberComments;
    private String contents;
  }
}
