package com.bucket.list.controller.bucketList;

import com.bucket.list.dto.bucketList.BucketListRequestDto;
import com.bucket.list.dto.bucketList.BucketListResponseDto;
import com.bucket.list.entity.bucketList.BucketList;
import com.bucket.list.entity.member.Member;
import com.bucket.list.mapper.bucketList.BucketListMapper;
import com.bucket.list.service.bucketList.BucketListGroupService;
import com.bucket.list.service.bucketList.BucketListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/bucket-groups/{bucketListGroupId}")
public class BucketListController {

    //버킷리스트 그룹안에 만들어야 되서 코드가 더 필요할거같은데 생각이 나지 않는다
    private final BucketListService bucketListService;
    private final BucketListGroupService bucketListGroupService;
    private final BucketListMapper mapper;

    public BucketListController(BucketListService bucketListService, BucketListGroupService bucketListGroupService, BucketListMapper mapper) {
        this.bucketListService = bucketListService;
        this.bucketListGroupService = bucketListGroupService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity createdBucketList(@Positive long bucketListGroupId, @Positive String title, @RequestBody BucketListRequestDto.createdBucketList createdBucketListDto){
        BucketList bucketList = mapper.createdBucketListPost(createdBucketListDto);
        BucketList newBucketList = bucketListService.createdBucketList(bucketList);

        return new ResponseEntity(mapper.createdBucketListResponse(newBucketList), HttpStatus.CREATED);
    }

    @PatchMapping("/list/{bucketList-Id}")
    public ResponseEntity updateBucketList(@PathVariable("/list.bucketList-id") @Positive long bucketListId, @RequestBody BucketListRequestDto.updateBucketList updateBucketListDto){
        updateBucketListDto.setBucketListId(bucketListId);

        BucketList updateBucketList = bucketListService.updateBucketList(mapper.updateBucketListPost(updateBucketListDto));

        return new ResponseEntity<>(mapper.updateBucketListResponse(updateBucketList),HttpStatus.OK);
    }

    @GetMapping("/list/{bucketList-Id}")
    public ResponseEntity findBucketList(@PathVariable ("/list.bucketList-id") @Positive long bucketListId){
        BucketList findBucketList = bucketListService.findBucketList(bucketListId);
        return new ResponseEntity<>(mapper.findBucketListResponse(findBucketList),HttpStatus.OK);
    }

    //완성 못함
//    @GetMapping("/list")
//    public ResponseEntity findBucketLists(){
//        List<BucketList> bucketLists = bucketListService.findBucketLists();
//
//        List<BucketListResponseDto.findBucketLists> response = bucketLists.stream()
//                .map(bucketList -> mapper.findsBucketListResponses(bucketListService,bucketLists))
//                .collect(Collectors.toList());
//    }

    @DeleteMapping("/list/{bucketList-Id}")
    public ResponseEntity deleteBucketList(@PathVariable ("/list.bucketList-id") @Positive long bucketListId){
        bucketListService.deleteBucketList(bucketListId);
        String s = "삭제 완료;";
        String message= s;
        return new ResponseEntity<>(mapper.deleteBucketListResponse(message),HttpStatus.NO_CONTENT);
    }
}
