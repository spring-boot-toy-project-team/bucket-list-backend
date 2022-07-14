package com.bucket.list.bucketList.dto;

import com.bucket.list.bucketList.entity.BucketList;
import com.bucket.list.bucketList.entity.BucketListGroup;
import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.List;

public class BucketListGroupResponseDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateGroupDto{

        private long bucketListGroupId;

        private String title;
        private int year;

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GroupInfo{
        private String title;
        private int year;
    }

}
