package com.bucket.list.bucketList.mapper;

import com.bucket.list.bucketList.dto.BucketListResponseDto;
import com.bucket.list.bucketList.entity.BucketList;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-19T04:06:24+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.15 (Azul Systems, Inc.)"
)
@Component
public class BucketListMapperImpl implements BucketListMapper {

    @Override
    public List<BucketListResponseDto.BucketListInfo> bucketListsToBucketListInfo(List<BucketList> bucketLists) {
        if ( bucketLists == null ) {
            return null;
        }

        List<BucketListResponseDto.BucketListInfo> list = new ArrayList<BucketListResponseDto.BucketListInfo>( bucketLists.size() );
        for ( BucketList bucketList : bucketLists ) {
            list.add( bucketListToBucketListInfo( bucketList ) );
        }

        return list;
    }
}
