//package com.bucket.list.entity.bucketList.service;
//
//import com.bucket.list.entity.bucketList.entity.BucketList;
//import com.bucket.list.entity.bucketList.entity.BucketListGroup;
//import com.bucket.list.entity.member.entity.Member;
//import com.bucket.list.entity.bucketList.repository.BucketListGroupRepository;
//import com.bucket.list.entity.bucketList.repository.BucketListRepository;
//import com.bucket.list.entity.member.service.MemberService;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class BucketListService {
//    private final MemberService memberService;
//    private final BucketListRepository bucketListRepository;
//    private final BucketListGroupRepository bucketListGroupRepository;
//
//    public BucketListService(MemberService memberService, BucketListRepository bucketListRepository, BucketListGroupRepository bucketListGroupRepository) {
//        this.memberService = memberService;
//        this.bucketListRepository = bucketListRepository;
//        this.bucketListGroupRepository = bucketListGroupRepository;
//    }
//
//    //버킷 리스트 등록
//    public BucketList createdBucketList(BucketList bucketList){
//        verifieBucketList(bucketList.getBuckListId());
//        return bucketListRepository.save(bucketList);
//    }
//
//    //버킷 리스트 조회
//    public BucketList findBucketList(long bucketListId){
//        BucketList findBucketList = verifieBucketList(bucketListId);
//        return findBucketList;
//    }
//
//    //버킷 리스트들 조회
//    public List<BucketList> findBucketLists(long memberId){
//        Member member = memberService.findMembers(memberId);
//        List<BucketList> findBucketLists = findBucketLists(memberId);
//        return findBucketLists;
//    }
//
//    //버킷 리스트들 조회
//    public List<BucketList> findBucketLists() {
//        return (List<BucketList>) bucketListRepository.findAll();
//    }
//
//    //버킷 리스트 삭제
//    public void deleteBucketList(long bucketListId){
//        BucketList deleteBucketList = verifieBucketList(bucketListId);
//        bucketListRepository.delete(deleteBucketList);
//    }
//
//    //버킷 리스트 변경
//    public BucketList updateBucketList(BucketList bucketListId){
//        BucketList updateBucketList = verifieBucketList(bucketListId.getBuckListId());
//        updateBucketList.setBuckListId(bucketListId.getBuckListId());
//        return bucketListRepository.save(updateBucketList);
//    }
//
//
//    public BucketListGroup verifieBucketListGroup(long bucketListGroupId){
//        Optional<BucketListGroup> optionalBucketListGroup = bucketListGroupRepository.findById(bucketListGroupId);
//        BucketListGroup findBuckListGroup = optionalBucketListGroup.get();
//        return findBuckListGroup;
//    }
//
//    public BucketList verifieBucketList(long bucketListId){
//        Optional<BucketList> optionalBucketList = bucketListRepository.findById(bucketListId);
//        BucketList findBuckList = optionalBucketList.get();
//        return findBuckList;
//    }
//
//}
