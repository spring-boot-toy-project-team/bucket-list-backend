package com.bucket.list.bucketList.dto;

import com.bucket.list.bucketList.entity.BucketList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class BucketListResponseDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BucketListInfo{
        private long bucketListId;
        @NotBlank
        private String target;

        private boolean completed;

    }


}
