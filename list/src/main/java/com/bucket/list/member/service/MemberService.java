package com.bucket.list.member.service;

import com.bucket.list.exception.BusinessLogicException;
import com.bucket.list.exception.ExceptionCode;
import com.bucket.list.member.entity.Member;
import com.bucket.list.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    // TO-DO : 휴대폰 번호 유니크 조건 검증!
    Optional.ofNullable(member.getNickName()).ifPresent(findMember::setNickName);
    Optional.ofNullable(member.getIntroduction()).ifPresent(findMember::setIntroduction);
    Optional.ofNullable(member.getTel()).ifPresent(findMember::setTel);
    Optional.ofNullable(member.getProfileImg()).ifPresent(findMember::setProfileImg);
    Optional.ofNullable(member.getPassword()).ifPresent(findMember::setPassword);
    Optional.ofNullable(member.getMemberStatus()).ifPresent(findMember::setMemberStatus);

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
      throw new BusinessLogicException(ExceptionCode.MEMBER_ALREADY_EXISTS);
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

  @Transactional(readOnly = true)
  public Member findVerifiedMemberByEmail(String email){
      Optional<Member> optionalMember = memberRepository.findByEmail(email);
    Member findMember = optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    if(findMember.getMemberStatus().equals(Member.MemberStatus.MEMBER_QUIT))
      throw new BusinessLogicException(ExceptionCode.MEMBER_IS_QUIT);
    if(findMember.getMemberStatus().equals(Member.MemberStatus.MEMBER_SLEEP))
      throw new BusinessLogicException(ExceptionCode.MEMBER_IS_SLEEP);
    return findMember;
  }
}
