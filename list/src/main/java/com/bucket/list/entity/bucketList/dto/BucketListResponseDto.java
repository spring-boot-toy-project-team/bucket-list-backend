package com.bucket.list.entity.bucketList.dto;

import com.bucket.list.entity.bucketList.BucketList;
import com.bucket.list.entity.bucketList.BucketListGroup;
import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.List;

public class BucketListResponseDto {

    //  버킷리스트 그룹 등록
    @Getter
    @AllArgsConstructor
    public static class createdBucketListGroup{
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long bucketListId;

        private String title;

        private Integer year;

        private String message;
    }

    //버킷리스트 그룹 조회
    @Getter
    @Setter
    @AllArgsConstructor
    public static class findBucketListGroup{
        private String title;

        private Integer year;

        private String message;
    }

    //버킷리스트 그룹들 조회
    @Getter
    @Setter
    @AllArgsConstructor
    public static class findBucketListGroups{
        private List<BucketListGroup> bucketListGroupList;

        private String message;
    }

    //버킷리스트 그룹 삭제
    @Getter
    @NoArgsConstructor
    public static class deleteBucketListGroup{
        private String message;
    }

    //버킷리스트 그룹 변경
    @Getter
    @AllArgsConstructor
    public static class updateBucketListGroup{
        private String title;

        private String message;
    }

    //버킷리스트 등록
    @Getter
    public static class createdBucketList{
        private String target;

        private Boolean isComplete;

        private String message;
    }

    //버킷리스트 조회
    @Getter
    @Setter
    @AllArgsConstructor
    public static class findBucketList{
        private String target;

        private Boolean isComplete;

        private String message;
    }

    //버킷리스트들 조회
    @Getter
    @Setter
    @AllArgsConstructor
    public static class findBucketLists{
        private List<BucketList> bucketLists;

        private String message;
    }

    //버킷리스트 삭제
    @Getter
    @NoArgsConstructor
    public static class deleteBucketList{
        private String message;
    }

    //버킷리스트 변경
    @Getter
    @AllArgsConstructor
    public static class updateBucketList{
        private long bucketListGroupId;

        private String target;

        private Boolean isComplete;

        private String message;
    }

}
