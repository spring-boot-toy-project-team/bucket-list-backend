package com.bucket.list.service.bucketList;

import com.bucket.list.entity.bucketList.BucketListGroup;
import com.bucket.list.entity.member.Member;
import com.bucket.list.repository.bucketList.BucketListGroupRepository;
import com.bucket.list.repository.member.MemberRepository;
import com.bucket.list.service.member.MemberService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BucketListGroupService {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final BucketListGroupRepository bucketListGroupRepository;

    public BucketListGroupService(MemberService memberService, MemberRepository memberRepository, BucketListGroupRepository bucketListGroupRepository) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
        this.bucketListGroupRepository = bucketListGroupRepository;
    }


    //버킷 리스트 그룹 등록
    public BucketListGroup createBucketListGroup(BucketListGroup bucketListGroup){
        verifieBucketListGroup(bucketListGroup.getBucketListGroupId());
        return bucketListGroupRepository.save(bucketListGroup);
    }

    //버킷 리스트 그룹 조회
    public BucketListGroup findBucketListGroup(long bucketListGroupId){
        BucketListGroup findBucketListGroup = verifieBucketListGroup(bucketListGroupId);
        return findBucketListGroup;
    }

    //버킷 리스트 그룹들 조회
    public List<BucketListGroup> findBucketListGroups(long memberId){
        Member member = memberService.findMembers(memberId);
        List<BucketListGroup> findBucketListGroups = findBucketListGroups(memberId);
        return findBucketListGroups;
    }

    //버킷 리스트 그룹들 조회
    public List<BucketListGroup> findBucketListGroups(){
        return (List<BucketListGroup>) bucketListGroupRepository.findAll();
    }

    //버킷 리스트 그룹 삭제
    public void deleteBucketListGroup(long bucketListGroupId){
        BucketListGroup deleteBucketListGroup = verifieBucketListGroup(bucketListGroupId);
        bucketListGroupRepository.delete(deleteBucketListGroup);
    }

    //버킷 리스트 그룹 변경
    public BucketListGroup updateBucketListGroup(long memberId){
        Member member = verifieMember(memberId);
        BucketListGroup updateBucketListGroup = updateBucketListGroup(memberId);
        return bucketListGroupRepository.save(updateBucketListGroup);
    }

    public BucketListGroup verifieBucketListGroup(long bucketListGroupId){
        Optional<BucketListGroup> optionalBucketListGroup = bucketListGroupRepository.findById(bucketListGroupId);
        BucketListGroup findBuckListGroup = optionalBucketListGroup.get();
        return findBuckListGroup;
    }

    public Member verifieMember(long memberId){
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member findMember = optionalMember.get();
        return findMember;
    }
}
