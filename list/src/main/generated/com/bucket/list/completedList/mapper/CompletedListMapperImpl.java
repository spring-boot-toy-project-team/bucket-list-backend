package com.bucket.list.completedList.mapper;

import com.bucket.list.completedList.dto.CompletedListResponseDto;
import com.bucket.list.completedList.entity.CompletedList;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-11T23:07:21+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.13 (Oracle Corporation)"
)
@Component
public class CompletedListMapperImpl implements CompletedListMapper {

    @Override
    public List<CompletedListResponseDto.CompletedListInfo> completeListsToCompletedInfoList(List<CompletedList> completedLists) {
        if ( completedLists == null ) {
            return null;
        }

        List<CompletedListResponseDto.CompletedListInfo> list = new ArrayList<CompletedListResponseDto.CompletedListInfo>( completedLists.size() );
        for ( CompletedList completedList : completedLists ) {
            list.add( completeListToCompletedInfo( completedList ) );
        }

        return list;
    }
}
