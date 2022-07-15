package com.bucket.list.member.service;
import com.bucket.list.member.entity.Member;
import com.bucket.list.member.dto.MemberRequestDto;
import com.bucket.list.member.repository.MemberRepository;
import com.bucket.list.exception.BusinessLogicException;
import com.bucket.list.exception.ExceptionCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member createMember(Member member) {
        verifyExistsEmail(member.getEmail());
        return memberRepository.save(member);
    }

    public Member updateMember(MemberRequestDto.UpdateDto updateDto){
        Member findMember = findVerifiedMember(updateDto.getMemberId());

        Optional.ofNullable(updateDto.getNickName())
                .ifPresent(nickName -> findMember.setNickName(nickName));
        Optional.ofNullable(updateDto.getIntroduction())
                .ifPresent(introduction -> findMember.setIntroduction(introduction));
        Optional.ofNullable(updateDto.getTel())
                .ifPresent(tel -> findMember.setTel(tel));
        Optional.ofNullable(updateDto.getProfileImg())
                .ifPresent(profileImg -> findMember.setProfileImg(profileImg));
        Optional.ofNullable(updateDto.getPassword())
                .ifPresent(password->findMember.setPassword(password));
        Optional.ofNullable(updateDto.getStatus())
                .ifPresent(status->findMember.setMemberStatus(findMember.getMemberStatus()));

        return memberRepository.save(findMember);
    }

    @Transactional(readOnly = true)
    public Member findMember(long memberId) {
        return findVerifiedMember(memberId);
    }

    public void deleteMember(long memberId){
        Member member = findVerifiedMember(memberId);
        memberRepository.delete(member);
    }

    public void verifyExistsEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if(member.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
        }
    }

    @Transactional(readOnly = true)
    public Member findVerifiedMember(long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);

        Member findMember = optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        if(findMember.getMemberStatus().equals(Member.MemberStatus.MEMBER_QUIT))
            throw new BusinessLogicException((ExceptionCode.MEMBER_NOT_FOUND));
        return findMember;
    }


//    public void MemberLogin (Member member){
//        accountService.join(member);
//    }

//    public void MemberLogout(long memberId){
//        Member findMember = verifieMember(memberId);
//    }

}

