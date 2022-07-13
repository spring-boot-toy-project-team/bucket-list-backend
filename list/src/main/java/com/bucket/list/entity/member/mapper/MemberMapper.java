package com.bucket.list.entity.member.mapper;

import com.bucket.list.entity.member.dto.MemberRequestDto;
import com.bucket.list.entity.member.dto.MemberResponseDto;
import com.bucket.list.entity.member.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    //request
    Member memberPostToMember(MemberRequestDto.memberSignIn memberSignInDto);
    Member memberPostToMember(MemberRequestDto.memberSignUpDto memberSignUpDto);
    Member memberPostToMember(MemberRequestDto.updateMember updateMemberDto);
    Member memberPostToMember(MemberRequestDto.memberLogOut memberLogOutDto);
    Member memberPostToMember(MemberRequestDto.findMember findMemberDto);

    //response 이부분을 어찌해야되는지 잘 모르겠습니다 응답이 전부 null로 나와서
    MemberResponseDto.memberSignIn memberToMemberSignIn(Member member);
    MemberResponseDto.memberSignUp memberToMemberSignUp(Member member);
    MemberResponseDto.updateMember memberToMemberModify(Member member);
    MemberResponseDto.memberLogOut memberToMemberLogOut(Member member);
    MemberResponseDto.findMember memberToMemberFindMember(Member member);


}
