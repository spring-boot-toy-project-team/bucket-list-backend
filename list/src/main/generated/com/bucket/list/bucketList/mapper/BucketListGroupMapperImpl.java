package com.bucket.list.bucketList.mapper;

import com.bucket.list.bucketList.dto.BucketListGroupRequestDto;
import com.bucket.list.bucketList.dto.BucketListGroupResponseDto;
import com.bucket.list.bucketList.entity.BucketListGroup;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-07-18T09:32:58+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.15 (Azul Systems, Inc.)"
)
@Component
public class BucketListGroupMapperImpl implements BucketListGroupMapper {

    @Override
    public BucketListGroupResponseDto.CreateGroupDto bucketListGroupToCreateGroupDto(BucketListGroup bucketListGroup) {
        if ( bucketListGroup == null ) {
            return null;
        }

        BucketListGroupResponseDto.CreateGroupDto.CreateGroupDtoBuilder createGroupDto = BucketListGroupResponseDto.CreateGroupDto.builder();

        createGroupDto.bucketListGroupId( bucketListGroup.getBucketListGroupId() );
        createGroupDto.title( bucketListGroup.getTitle() );
        createGroupDto.year( bucketListGroup.getYear() );

        return createGroupDto.build();
    }

    @Override
    public BucketListGroupResponseDto.GroupInfo bucketListGroupToGroupInfo(BucketListGroup bucketListGroup) {
        if ( bucketListGroup == null ) {
            return null;
        }

        BucketListGroupResponseDto.GroupInfo.GroupInfoBuilder groupInfo = BucketListGroupResponseDto.GroupInfo.builder();

        groupInfo.title( bucketListGroup.getTitle() );
        groupInfo.year( bucketListGroup.getYear() );

        return groupInfo.build();
    }

    @Override
    public List<BucketListGroupResponseDto.GroupInfo> bucketListGroupsToGroupInfo(List<BucketListGroup> bucketListGroups) {
        if ( bucketListGroups == null ) {
            return null;
        }

        List<BucketListGroupResponseDto.GroupInfo> list = new ArrayList<BucketListGroupResponseDto.GroupInfo>( bucketListGroups.size() );
        for ( BucketListGroup bucketListGroup : bucketListGroups ) {
            list.add( bucketListGroupToGroupInfo( bucketListGroup ) );
        }

        return list;
    }

    @Override
    public BucketListGroup updateGroupDtoToBucketListGroup(BucketListGroupRequestDto.UpdateGroupDto updateGroupDto) {
        if ( updateGroupDto == null ) {
            return null;
        }

        BucketListGroup bucketListGroup = new BucketListGroup();

        bucketListGroup.setBucketListGroupId( updateGroupDto.getBucketListGroupId() );
        bucketListGroup.setTitle( updateGroupDto.getTitle() );

        return bucketListGroup;
    }
}
