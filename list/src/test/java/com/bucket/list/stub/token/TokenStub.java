package com.bucket.list.stub.token;

import com.bucket.list.dto.token.TokenRequestDto;
import com.bucket.list.dto.token.TokenResponseDto;


public class TokenStub {
  public static TokenResponseDto.Token createToken() {
    return TokenResponseDto.Token.builder()
      .grantType("Bearer")
      .accessToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZ2RAZ21haWwuY29tIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTY2MDMwNjAzNiwiZXhwIjoxNjYwMzA3ODM2fQ.lw9ZxYQ6zMU-m8VHH-nxUhnx8snGRxMaGGVOR9aOzbA")
      .refreshToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NjAzOTI0MzZ9.8cGV6BqsCOPNpxbyPat5Hf8jAFoEEuRLx1618w_KvJk")
      .accessTokenExpiredTime(1800000L)
      .refreshTokenExpiredTime(86400000L)
      .build();
  }

  public static TokenRequestDto.ReIssue reIssue() {
    return TokenRequestDto.ReIssue.builder()
      .accessToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZ2RAZ21haWwuY29tIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTY2MDMwNjAzNiwiZXhwIjoxNjYwMzA3ODM2fQ.lw9ZxYQ6zMU-m8VHH-nxUhnx8snGRxMaGGVOR9aOzbA")
      .refreshToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NjAzOTI0MzZ9.8cGV6BqsCOPNpxbyPat5Hf8jAFoEEuRLx1618w_KvJk")
      .build();
  }

  public static TokenResponseDto.ReIssueToken getReIssue() {
    return TokenResponseDto.ReIssueToken.builder()
      .grantType("Bearer")
      .accessToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZ2RAZ21haWwuY29tIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTY2MDMwNjI4NCwiZXhwIjoxNjYwMzA4MDg0fQ.ginNA_fv4nvkcHj7jBngcNWyu8spq9I9Nnx_g5-pq1w")
      .accessTokenExpiredTime(1800000L)
      .build();
  }
}
