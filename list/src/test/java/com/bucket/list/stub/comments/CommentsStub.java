package com.bucket.list.stub.comments;

import com.bucket.list.comment.dto.CommentsRequestDto;
import com.bucket.list.comment.entity.Comments;
import com.bucket.list.completedList.entity.CompletedList;
import com.bucket.list.member.entity.Member;
import com.bucket.list.stub.member.MemberStub;

import javax.xml.stream.events.Comment;

public class CommentsStub {
  private static final Member member = MemberStub.getMember();
  public static CommentsRequestDto.CreateCommentsDto createCommentsDto(long completedListId, String contents) {
    return CommentsRequestDto.CreateCommentsDto.builder()
      .completedListId(completedListId)
      .contents(contents)
      .memberId(member.getMemberId())
      .build();
  }


}
