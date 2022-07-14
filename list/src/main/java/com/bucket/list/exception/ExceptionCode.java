package com.bucket.list.exception;

import lombok.Getter;

public enum ExceptionCode {
  MEMBER_NOT_FOUND(404, "Member not found"),
  MEMBER_EXISTS(409, "Member exists"),
  MEMBER_IS_SLEEP(404, "Member is sleep"),
  MEMBER_IS_QUIT(404, "Member is quit"),
  FIELD_ERROR(400, "Field Error"),
  CONSTRAINT_VIOLATION_ERROR(400, "Constraint Violation Error"),
  NOT_IMPLEMENTATION(501, "Not Implementation"),
  INVALID_MEMBER_STATUS(400, "Invalid member status"),
  BUCKET_LIST_GROUP_NOT_FOUND(404, "Bucket List Group not found" );

  @Getter
  private int status;

  @Getter
  private String message;

  ExceptionCode(int code, String message) {
    this.status = code;
    this.message = message;
  }
}
