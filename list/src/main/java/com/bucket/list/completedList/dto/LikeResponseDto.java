package com.bucket.list.completedList.dto;

import lombok.*;

public class LikeResponseDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LikeInfo{
        private long memberId;
        private long completedListId;
    }
}
