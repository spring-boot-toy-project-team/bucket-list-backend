//package com.bucket.list.entity.bucketList.controller;

//import com.bucket.list.entity.bucketList.dto.BucketListRequestDto;
//import com.bucket.list.entity.bucketList.entity.BucketListGroup;
//import com.bucket.list.entity.bucketList.mapper.BucketListGroupMapper;
//import com.bucket.list.entity.bucketList.service.BucketListGroupService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.constraints.Positive;

//@RestController
//@RequestMapping("/v1/bucket-groups")
//public class BucketListGroupController {
//
//    private final BucketListGroupService bucketListGroupService;
//    private final BucketListGroupMapper mapper;
//
//    public BucketListGroupController(BucketListGroupService bucketListGroupService, BucketListGroupMapper mapper) {
//        this.bucketListGroupService = bucketListGroupService;
//        this.mapper = mapper;
//    }
//
//    @PostMapping
//    public ResponseEntity createdBucketListGroup(@RequestBody BucketListRequestDto.createdBucketListGroup createdBucketListGroupDto){
//        BucketListGroup bucketListGroup = mapper.createdBucketListGroupPost(createdBucketListGroupDto);
//        BucketListGroup newBucketListGroup = bucketListGroupService.createBucketListGroup(bucketListGroup);
//
//        return new ResponseEntity(mapper.createdBucketListGroupResponse(newBucketListGroup), HttpStatus.CREATED);
//    }
//
//    @PatchMapping("/{bucketListGroup-id}")
//    public ResponseEntity updateBucketList(@PathVariable("bucketListGroup-id") @Positive long memderId, @RequestBody BucketListRequestDto.updateBucketListGroup updateBucketListGroupDto){
//        updateBucketListGroupDto.setMemberId(memderId);
//        BucketListGroup updateBucketListGroup = bucketListGroupService.updateBucketListGroup(memderId);
//        return new ResponseEntity<>(mapper.updateBucketListGroupResponse(updateBucketListGroup),HttpStatus.OK);
//    }
//
//    @GetMapping("/{bucketListGroup-id}")
//    public ResponseEntity findBucketList(@PathVariable ("bucketListGroup-id") @Positive long bucketListGroupId){
//        BucketListGroup findBucketGroup = bucketListGroupService.findBucketListGroup(bucketListGroupId);
//        return new ResponseEntity<>(mapper.findBucketListGroupResponse(findBucketGroup),HttpStatus.OK);
//    }
//
//    //완성 못함
////    @GetMapping("/list")
////    public ResponseEntity findBucketLists(){
////        List<BucketList> bucketLists = bucketListService.findBucketLists();
////
////        List<BucketListResponseDto.findBucketLists> response = bucketLists.stream()
////                .map(bucketList -> mapper.findsBucketListResponses(bucketListService,bucketLists))
////                .collect(Collectors.toList());
////    }
//
//    @DeleteMapping("/{bucketListGroup-id}")
//    public ResponseEntity deleteBucketList(@PathVariable ("bucketListGroup-id") @Positive long bucketListGroupId){
//       bucketListGroupService.deleteBucketListGroup(bucketListGroupId);
//        String s = "삭제 완료;";
//        String message= s;
//        return new ResponseEntity<>(mapper.deleteBucketListGroupResponse(message),HttpStatus.NO_CONTENT);
//    }
//}

