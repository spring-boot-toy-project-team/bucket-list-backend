package com.bucket.list.completedList.mapper;

import com.bucket.list.completedList.dto.LikeResponseDto;
import com.bucket.list.completedList.entity.CompletedList;
import com.bucket.list.completedList.entity.Like;
import com.bucket.list.member.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LikeMapper {
  default Like createLike(long completedListId, long memberId) {
    Member member = new Member();
    member.setMemberId(memberId);
    CompletedList completedList = new CompletedList();
    completedList.setCompletedListId(completedListId);
    Like like = new Like();
    like.setMember(member);
    like.setCompletedList(completedList);
    return like;
  }

  default LikeResponseDto.LikeInfo likeToLikeInfo(Like like) {
    return LikeResponseDto.LikeInfo.builder()
      .completedListId(like.getCompletedList().getCompletedListId())
      .memberId(like.getMember().getMemberId())
      .build();
  }
}
