//package com.bucket.list.entity.bucketList.mapper;
//
//import com.bucket.list.entity.bucketList.dto.BucketListRequestDto;
//import com.bucket.list.entity.bucketList.dto.BucketListResponseDto;
//import com.bucket.list.entity.bucketList.entity.BucketList;
//import org.mapstruct.Mapper;
//
//import java.util.List;
//
//@Mapper(componentModel = "spring")
//public interface BucketListMapper {
//
//    //default BucketList createdBucketListPost(BucketListRequestDto.createdBucketList createdBucketListDto){
////        BucketListGroup bucketListGroup = new BucketListGroup();
////        BucketList bucketList = new BucketList();
////
////        bucketList.setBuckListId(createdBucketListDto.);
////    }
//    BucketList createdBucketListPost(BucketListRequestDto.createdBucketList createdBucketListDto);
//    BucketList updateBucketListPost(BucketListRequestDto.updateBucketList updateBucketListDto);
//    BucketList findBucketListPost(BucketListRequestDto.findBucketList findBucketListDto);
//    BucketList findBucketListsPost(BucketListRequestDto.findBucketLists findBucketListsDto);
//    BucketList deleteBucketListPost(String message);
//
//
//    BucketListResponseDto.createdBucketList createdBucketListResponse(BucketList bucketList);
//    BucketListResponseDto.updateBucketList updateBucketListResponse(BucketList bucketList);
//    BucketListResponseDto.findBucketList findBucketListResponse(BucketList bucketList);
////    BucketListResponseDto.findBucketLists findsBucketListResponses(List<BucketList> bucketLists);
//    BucketListResponseDto.deleteBucketList deleteBucketListResponse(String message);
//}