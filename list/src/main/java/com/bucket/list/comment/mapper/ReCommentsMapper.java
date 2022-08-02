package com.bucket.list.comment.mapper;

import com.bucket.list.comment.dto.ReCommentsRequestDto;
import com.bucket.list.comment.dto.ReCommentsResponseDto;
import com.bucket.list.comment.entity.Comments;
import com.bucket.list.comment.entity.ReComments;
import com.bucket.list.member.dto.MemberResponseDto;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ReCommentsMapper {
  default ReComments createReCommentsDtoToReComments(ReCommentsRequestDto.CreateReCommentsDto createReCommentsDto) {
    ReComments reComments = new ReComments();
    Comments comments = new Comments();
    comments.setCommentsId(createReCommentsDto.getCommentsId());
    reComments.setComments(comments);
    reComments.setContents(createReCommentsDto.getContents());
    return reComments;
  }

  default ReCommentsResponseDto.ReCommentsInfo reCommentsToReCommentsInfo(ReComments reComments) {
    return ReCommentsResponseDto.ReCommentsInfo.builder()
      .reCommentsId(reComments.getReCommentsId())
      .memberComments(MemberResponseDto.MemberComments.builder()
        .nickName(reComments.getMember().getNickName())
        .profileImg(reComments.getMember().getProfileImg())
        .build())
      .contents(reComments.getContents())
      .build();
  }

  default List<ReCommentsResponseDto.ReCommentsInfo> reCommentsListToReCommentsInfoList(List<ReComments> reCommentsList) {
    return reCommentsList.stream()
      .map(this::reCommentsToReCommentsInfo)
      .collect(Collectors.toList());
  }

  default ReComments updateReCommentsDtoToReComments(ReCommentsRequestDto.UpdateReCommentsDto updateReCommentsDto) {
    ReComments reComments = new ReComments();
    Comments comments = new Comments();
    comments.setCommentsId(updateReCommentsDto.getCommentsId());
    reComments.setReCommentsId(updateReCommentsDto.getReCommentsId());
    reComments.setComments(comments);
    reComments.setContents(updateReCommentsDto.getContents());
    return reComments;
  }
}
