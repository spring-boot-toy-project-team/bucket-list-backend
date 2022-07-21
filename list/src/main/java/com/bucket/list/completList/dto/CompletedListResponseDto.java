package com.bucket.list.completList.dto;

import com.bucket.list.completList.entity.Img;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class CompletedListResponseDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CompletedListInfo {
        private String contents;
        private long completedListId;
        private String tags;
        private List<Img> imgs;
        private long bucketListId;
        private long bucketListGroupId;
    }

//    @Data
//    @NoArgsConstructor
//    @AllArgsConstructor
//    @Builder
//    public static class UpdateCompletedListDto {
//        private String contents;
//        private long completedListId;
//        private List<TagResponseDto.TagInfo> tags;
//        private List<Img> imgs;
//        private long bucketListId;
//        private long bucketListGroupId;
//    }
}
