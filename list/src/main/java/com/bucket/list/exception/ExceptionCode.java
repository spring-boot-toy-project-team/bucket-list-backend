package com.bucket.list.exception;

import lombok.Getter;

public enum ExceptionCode {
  MEMBER_NOT_FOUND(404, "Member not found"),
  MEMBER_ALREADY_EXISTS(409, "Member already exists"),
  MEMBER_IS_SLEEP(404, "Member is sleep"),
  MEMBER_IS_QUIT(404, "Member is quit"),
  FIELD_ERROR(400, "Field Error"),
  CONSTRAINT_VIOLATION_ERROR(400, "Constraint Violation Error"),
  NOT_IMPLEMENTATION(501, "Not Implementation"),
  INVALID_MEMBER_STATUS(400, "Invalid member status"),
  TAG_NOT_FOUND(404, "Tag not found" ),
  BUCKET_LIST_GROUP_NOT_FOUND(404, "Bucket List Group not found" ),
  BUCKET_LIST_NOT_FOUND(404, "Bucket List not found" ),
  COMPLETED_LIST_NOT_FOUND(404, "Completed List not found"),
  COMPLETED_LIST_ALREADY_EXISTS(409, "Completed List already exists"),
  ROLE_IS_NOT_EXISTS(403, "Role is not exists"),
  PASSWORD_INCORRECT(404, "Password Incorrect"),
  TOKEN_IS_INVALID(401, "Token is invalid"),
  REFRESH_TOKEN_IS_EXPIRED(403, "Refresh Token is expired"),
  COMMENTS_IS_NOT_FOUND(404, "Comments is not found"),
  RE_COMMENTS_IS_NOT_FOUND(404, "ReComments is not found"),
  LIKE_NOT_FOUND(404, "Like is not found"), 
  LIKE_ALREADY_EXISTS(409, "Like is already exists");

  @Getter
  private int status;

  @Getter
  private String message;

  ExceptionCode(int code, String message) {
    this.status = code;
    this.message = message;
  }
}
