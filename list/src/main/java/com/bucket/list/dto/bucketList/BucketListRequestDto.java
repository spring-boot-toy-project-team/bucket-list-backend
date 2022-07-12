package com.bucket.list.dto.bucketList;

import com.bucket.list.entity.bucketList.BucketList;
import com.bucket.list.entity.bucketList.BucketListGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

public class BucketListRequestDto {

    //  버킷리스트 그룹 등록
    @Getter
    @AllArgsConstructor
    public static class createdBucketListGroup{
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long memberId;

        private String title;
    }

    //버킷리스트 그룹 조회
    @Getter
    @AllArgsConstructor
    public static class findBucketListGroup{
        private long bucketListGroupId;
    }

    //버킷리스트 그룹들 조회
    @Getter
    @AllArgsConstructor
    public static class findBucketListGroups{
        private long memberId;
    }

    //버킷리스트 그룹 삭제
    @Getter
    public static class deleteBucketListGroup{
        private long bucketListGroupId;
    }

    //버킷리스트 그룹 변경
    @Getter
    @Setter
    public static class updateBucketListGroup{
        private long memberId;

        private String title;
    }

    //버킷리스트 등록
    @Getter
    @AllArgsConstructor
    public static class createdBucketList{
        private long bucketListGroupId;

        private long bucketListId;
    }

    //버킷리스트 조회
    @Getter
    @AllArgsConstructor
    public static class findBucketList{
        private long bucketListGroupId;

        private long bucketListId;
    }

    //버킷리스트들 조회
    @Getter
    @AllArgsConstructor
    public static class findBucketLists{
        private long bucketListGroupId;
    }

    //버킷리스트 삭제
    @Getter
    public static class deleteBucketList{
        private long bucketListGroupId;

        private long bucketListId;
    }

    //버킷리스트 변경
    @Getter
    @Setter
    public static class updateBucketList{
        private long bucketListGroupId;

        private long bucketListId;

        private String target;

        private Boolean isComplete;
    }

}
