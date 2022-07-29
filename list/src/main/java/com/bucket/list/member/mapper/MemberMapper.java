package com.bucket.list.member.mapper;

import com.bucket.list.member.dto.MemberRequestDto;
import com.bucket.list.member.dto.MemberResponseDto;
import com.bucket.list.member.entity.Member;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface MemberMapper {
  Member signUpDtoToMember(MemberRequestDto.SignUpDto signUpDto);

  MemberResponseDto.MemberInfo memberToMemberInfo(Member member);

  MemberResponseDto.UpdateDto memberToUpdateDto(Member member);

  Member updateDtoToMember(MemberRequestDto.UpdateDto updateDto);

    Member loginDtoToMember(MemberRequestDto.loginDto loginDto);
}
