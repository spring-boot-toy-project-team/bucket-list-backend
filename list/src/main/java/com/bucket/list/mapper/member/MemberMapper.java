package com.bucket.list.mapper.member;

import com.bucket.list.dto.member.MemberRequestDto;
import com.bucket.list.dto.member.MemberResponseDto;
import com.bucket.list.entity.member.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    //request
    Member memberPostToMember(MemberRequestDto.memberSignIn memberSignInDto);
    Member memberPostToMember(MemberRequestDto.memberSignUpDto memberSignUpDto);
    Member memberPostToMember(MemberRequestDto.modifyMember modifyMemberDto);
    Member memberPostToMember(MemberRequestDto.memberLogOut memberLogOutDto);
    Member memberPostToMember(MemberRequestDto.findMember findMemberDto);

    //response 이부분을 어찌해야되는지 잘 모르겠습니다 응답이 전부 null로 나와서
    MemberResponseDto.memberSignIn memberToMemberSignIn(Member member);
    MemberResponseDto.memberSignUpDto memberToMemberSignUp(Member member);
    MemberResponseDto.modifyMember memberToMemberModify(Member member);
    MemberResponseDto.memberLogOut memberToMemberLogOut(Member member);
    MemberResponseDto.findMember memberToMemberFindMember(Member member);


}
