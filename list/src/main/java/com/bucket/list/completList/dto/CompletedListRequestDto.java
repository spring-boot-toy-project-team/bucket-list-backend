package com.bucket.list.completList.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

public class CompletedListRequestDto {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreateCompletedListDto {
        private long groupId;
        private long bucketListId;
        @NotBlank
        private String contents;
        private String tags;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpdateCompletedListDto{
        private long completedListId;
        private long groupId;
        private long bucketListId;
        private long tagId;
        @NotBlank
        private String contents;
        private String tags;
    }
}