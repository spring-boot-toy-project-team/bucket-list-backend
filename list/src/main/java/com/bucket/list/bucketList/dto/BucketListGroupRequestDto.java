package com.bucket.list.bucketList.dto;

import lombok.*;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BucketListGroupRequestDto {

    //  버킷리스트 그룹 등록
    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateGroupDto{

        @NotNull
        private long memberId;

        @NotBlank
        private String title;

    }
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateGroupDto{

        private long bucketListGroupId;

        @NotNull
        private long memberId;

        @NotBlank
        private String title;
    }

}
