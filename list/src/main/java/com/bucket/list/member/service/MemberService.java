package com.bucket.list.member.service;

import com.bucket.list.exception.BusinessLogicException;
import com.bucket.list.exception.ExceptionCode;
import com.bucket.list.member.dto.MemberRequestDto;
import com.bucket.list.member.entity.Member;
import com.bucket.list.member.mapper.MemberMapper;
import com.bucket.list.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
  private final MemberRepository memberRepository;

  public Member createMember(Member member) {
    verifyExistsEmail(member.getEmail());
    return memberRepository.save(member);
  }

  @Transactional(readOnly = true)
  public Member findMember(long memberId) {
    return findVerifiedMember(memberId);
  }

  public Member updateMember(Member member) {
    Member findMember = findVerifiedMember(member.getMemberId());

    Optional.ofNullable(member.getNickName()).ifPresent(nickName -> findMember.setNickName(nickName));
    Optional.ofNullable(member.getIntroduction()).ifPresent(introduction -> findMember.setIntroduction(introduction));
    Optional.ofNullable(member.getTel()).ifPresent(tel -> findMember.setTel(tel));
    Optional.ofNullable(member.getProfileImg()).ifPresent(profileImg -> findMember.setProfileImg(profileImg));
    Optional.ofNullable(member.getPassword()).ifPresent(password -> findMember.setPassword(password));
    Optional.ofNullable(member.getMemberStatus()).ifPresent(memberStatus -> findMember.setMemberStatus(memberStatus));
    findMember.setModifiedAt(LocalDateTime.now());

    return memberRepository.save(findMember);
  }

  public void deleteMember(long memberId) {
    Member member = findVerifiedMember(memberId);
    member.setMemberStatus(Member.MemberStatus.MEMBER_QUIT);
    memberRepository.save(member);
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
      throw new BusinessLogicException(ExceptionCode.MEMBER_IS_QUIT);
    if(findMember.getMemberStatus().equals(Member.MemberStatus.MEMBER_SLEEP))
      throw new BusinessLogicException(ExceptionCode.MEMBER_IS_SLEEP);
    return findMember;
  }
}
