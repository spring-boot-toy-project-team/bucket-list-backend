package com.bucket.list.service.member;
import com.bucket.list.entity.member.Member;
import com.bucket.list.repository.member.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //회원가입
    public Member createMember(Member member){
        MemberEmail(member.getEmail());
        Member createdMember = new Member();
        return memberRepository.save(createdMember);
    }

    //회원 수정
    public Member updateMember(Member member){
        Member updateMember = verifieMember(member.getMemberId());
        return memberRepository.save(updateMember);
    }

    //회원 조회
        public Member findMembers(long memberId){
        Member findMember = verifieMember(memberId);
        return findMember;
    }

    //회원 삭제
        public void deleteMember(long memberId){
        Member deleteMember = verifieMember(memberId);
        memberRepository.delete(deleteMember);
    }

//    public void MemberLogin (Member member){
//        accountService.join(member);
//    }

//    public void MemberLogout(long memberId){
//        Member findMember = verifieMember(memberId);
//    }

    public Member verifieMember(long memberId){
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member findMember = optionalMember.get();
        return findMember;
    }
    public void MemberEmail(String email){
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
    }
}

