package com.bucket.list.comment.mapper;

import com.bucket.list.comment.dto.CommentsRequestDto;
import com.bucket.list.comment.dto.CommentsResponseDto;
import com.bucket.list.comment.entity.Comments;
import com.bucket.list.completedList.entity.CompletedList;
import com.bucket.list.member.dto.MemberResponseDto;
import com.bucket.list.member.entity.Member;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CommentsMapper {

  default Comments createCommentsDtoToComments(CommentsRequestDto.CreateCommentsDto createCommentsDto) {
    Comments comments = new Comments();
    Member member = new Member();
    member.setMemberId(createCommentsDto.getMemberId());
    comments.setMember(member);
    CompletedList completedList = new CompletedList();
    completedList.setCompletedListId(createCommentsDto.getCompletedListId());
    comments.setCompletedList(completedList);
    comments.setContents(createCommentsDto.getContents());
    return comments;
  }

  default CommentsResponseDto.CommentsInfo commentsToCommentsInfo(Comments comments) {
    return CommentsResponseDto.CommentsInfo.builder()
      .commentsId(comments.getCommentsId())
      .contents(comments.getContents())
      .memberComments(MemberResponseDto.MemberComments.builder()
        .nickName(comments.getMember().getNickName())
        .profileImg(comments.getMember().getProfileImg())
        .build())
      .build();
  }

  default List<CommentsResponseDto.CommentsInfo> commentsListToCommentsInfoList(List<Comments> commentsList) {
    return commentsList.stream().map(this::commentsToCommentsInfo)
      .collect(Collectors.toList());
  }

  default Comments updateCommentsDtoToComments(CommentsRequestDto.UpdateCommentsDto updateCommentsDto) {
    Comments comments = new Comments();
    Member member = new Member();
    member.setMemberId(updateCommentsDto.getMemberId());
    comments.setMember(member);
    CompletedList completedList = new CompletedList();
    completedList.setCompletedListId(updateCommentsDto.getCompletedListId());
    comments.setCommentsId(updateCommentsDto.getCommentsId());
    comments.setCompletedList(completedList);
    comments.setContents(updateCommentsDto.getContents());
    return comments;
  }
}
