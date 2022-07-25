package com.bucket.list.member.mapper;

import com.bucket.list.member.dto.MemberRequestDto;
import com.bucket.list.member.dto.MemberResponseDto;
import com.bucket.list.member.entity.Member;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-07-25T22:05:49+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.15 (Azul Systems, Inc.)"
)
@Component
public class MemberMapperImpl implements MemberMapper {

    @Override
    public Member signUpDtoToMember(MemberRequestDto.SignUpDto signUpDto) {
        if ( signUpDto == null ) {
            return null;
        }

        Member member = new Member();

        member.setEmail( signUpDto.getEmail() );
        member.setPassword( signUpDto.getPassword() );
        member.setNickName( signUpDto.getNickName() );
        member.setTel( signUpDto.getTel() );
        member.setIntroduction( signUpDto.getIntroduction() );
        member.setProfileImg( signUpDto.getProfileImg() );

        return member;
    }

    @Override
    public MemberResponseDto.MemberInfo memberToMemberInfo(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberResponseDto.MemberInfo.MemberInfoBuilder memberInfo = MemberResponseDto.MemberInfo.builder();

        memberInfo.email( member.getEmail() );
        memberInfo.nickName( member.getNickName() );
        memberInfo.tel( member.getTel() );
        memberInfo.introduction( member.getIntroduction() );
        memberInfo.profileImg( member.getProfileImg() );

        return memberInfo.build();
    }

    @Override
    public MemberResponseDto.UpdateDto memberToUpdateDto(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberResponseDto.UpdateDto.UpdateDtoBuilder updateDto = MemberResponseDto.UpdateDto.builder();

        updateDto.nickName( member.getNickName() );
        updateDto.tel( member.getTel() );
        updateDto.introduction( member.getIntroduction() );
        updateDto.profileImg( member.getProfileImg() );
        updateDto.memberId( member.getMemberId() );

        return updateDto.build();
    }

    @Override
    public Member updateDtoToMember(MemberRequestDto.UpdateDto updateDto) {
        if ( updateDto == null ) {
            return null;
        }

        Member member = new Member();

        member.setMemberId( updateDto.getMemberId() );
        member.setPassword( updateDto.getPassword() );
        member.setNickName( updateDto.getNickName() );
        member.setTel( updateDto.getTel() );
        member.setIntroduction( updateDto.getIntroduction() );
        member.setProfileImg( updateDto.getProfileImg() );

        return member;
    }
}
