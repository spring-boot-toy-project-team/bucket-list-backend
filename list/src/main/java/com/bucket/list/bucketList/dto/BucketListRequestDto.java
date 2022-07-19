package com.bucket.list.bucketList.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class BucketListRequestDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreateBucketListDto{
        @NotBlank
        private String target;

        private long groupId;


    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpdateBucketListDto{

        private long bucketListId;

        @NotBlank
        private String target;

        private long groupId;

        @NotNull
        private boolean isCompleted;
    }
}
