package com.bucket.list.dto.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class TokenDto {
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Token {
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiredTime;
    private Long refreshTokenExpiredTime;
  }


  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class ReIssue{
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiredTime;
    private Long refreshTokenExpiredTime;
  }
}
