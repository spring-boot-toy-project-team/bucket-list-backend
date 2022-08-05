package com.bucket.list.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MemberResponseDto {

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class MemberInfo {
    private String email;
    private String nickName;
    private String tel;
    private String introduction;
    private String profileImg;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class UpdateDto {
    private String nickName;
    private String tel;
    private String introduction;
    private String profileImg;
    private long memberId;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class MemberComments{
    private String nickName;
    private String profileImg;

  }
}
