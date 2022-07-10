package com.bucket.list.service;


import com.bucket.list.entity.member.Member;
import com.bucket.list.repository.MemberRepository;
import com.bucket.list.requestDto.member.MemberPostDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final AccountService accountService;

    public MemberService(MemberRepository memberRepository, AccountService accountService) {
        this.memberRepository = memberRepository;
        this.accountService = accountService;
    }

    public Member createdMember(MemberPostDto member){
        Member createdMember = new Member();

        return memberRepository.save(createdMember);
    }

    public Member updateMember(Member member){
        Member findMember = verifieMember(member.getMemberId());
        return memberRepository.save(findMember);
    }

    public Member findMembers(long memberId){
        Member findMember = verifieMember(memberId);
        return findMember;
    }

    public void deleteMember(long memberId){
        Member findMember = verifieMember(memberId);
        memberRepository.delete(findMember);
    }

//    public void MemberLogin (Member member){
//        accountService.join(member);
//    }
//
//    public void MemberLogout(long memberId){
//        Member findMember = verifieMember(memberId);
//    }

    public Member verifieMember(long memberId){
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member findMember = optionalMember.get();
        return findMember;
    }

//    public void MemberEmail(String email){
//        Optional<Member> optionalMember = memberRepository.findByEmail(email);
//    }

}
