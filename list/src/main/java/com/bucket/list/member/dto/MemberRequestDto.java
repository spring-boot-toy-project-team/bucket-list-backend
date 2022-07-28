package com.bucket.list.member.dto;

import com.bucket.list.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


public class MemberRequestDto {
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class loginDto {
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z\\d_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z\\d.-]+$")
    private String email;
    @NotBlank
    private String password;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class SignUpDto {
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z\\d_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z\\d.-]+$")
    private String email;
    @NotBlank
    private String password;
    private String profileImg;
    private String introduction;
    @NotBlank
    private String nickName;
    @NotBlank
    @Pattern(regexp = "^010-\\d{3,4}-\\d{4}$")
    private String tel;
    private String provider;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class UpdateDto {
    private String nickName;
    private String tel;
    private String password;
    private String introduction;
    private String profileImg;
    private long memberId;
    private Member.MemberStatus status;
  }
}
