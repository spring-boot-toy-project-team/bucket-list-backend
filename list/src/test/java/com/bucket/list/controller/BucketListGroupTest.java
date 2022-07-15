package com.bucket.list.controller;

import com.bucket.list.bucketList.dto.BucketListGroupRequestDto;
import com.bucket.list.bucketList.entity.BucketListGroup;
import com.bucket.list.bucketList.mapper.BucketListGroupMapper;
import com.bucket.list.bucketList.service.BucketListGroupService;
import com.bucket.list.member.dto.MemberRequestDto;
import com.bucket.list.member.entity.Member;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BucketListGroupTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private BucketListGroupService bucketListGroupService;

    @Autowired
    private BucketListGroupMapper mapper;

//    @BeforeEach
//    public void init() throws Exception{
//        //given
//        BucketListGroupRequestDto.CreateGroupDto createGroupDto = BucketListGroupRequestDto.CreateGroupDto.builder()
//                .memberId(1L)
//                .title("sun")
//                .build();
//        String content = gson.toJson(createGroupDto);
//
//        //when
//        ResultActions actions = mockMvc.perform(
//                post("/v1/bucket-groups")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(content)
//        );
//    }
//    @BeforeEach
//    public void member() throws Exception {
//        MemberRequestDto.SignUpDto signUpDto = MemberRequestDto.SignUpDto.builder()
//                .email("hgd@gmail.com")
//                .introduction("홍길동 입니다.")
//                .nickName("홍길동")
//                .password("1234")
//                .tel("010-1234-5678")
//                .build();
//        String content = gson.toJson(signUpDto);
//
//         //when
//        ResultActions actions = mockMvc.perform(
//                post("/v1/members")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(content)
//        );
//    }
    @Test
    @DisplayName("버킷리스트 그룹생성 테스트")
    public void createBucketGroup() throws Exception{
        //given
        long memberId =1L;
        BucketListGroupRequestDto.CreateGroupDto createGroupDto = BucketListGroupRequestDto.CreateGroupDto.builder()
                .memberId(1L)
                .title("sun")
                .build();

        BucketListGroup bucketListGroup = mapper.groupDtoToBucketListGroup(createGroupDto);

        given(bucketListGroupService.createGroup(Mockito.any(BucketListGroup.class))).willReturn(bucketListGroup);
        String content = gson.toJson(createGroupDto);

        //when
        ResultActions actions = mockMvc.perform(
                post("/v1/bucket-groups")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        //then
        MvcResult result = actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("CREATED"))
                .andExpect(jsonPath("$.data.title").value(createGroupDto.getTitle()))
                .andReturn();
    }

    @Test
    @DisplayName("버킷리스트 그룹 변경")
    public void updateBucketGroup() throws Exception{
        //given
        long memberId =1L;
        long bucketListGroupId = 1L;
        BucketListGroupRequestDto.UpdateGroupDto updateGroupDto = BucketListGroupRequestDto.UpdateGroupDto.builder()
                .bucketListGroupId(1L)
                .memberId(1L)
                .title("moon")
                .build();

        BucketListGroup bucketListGroup = mapper.updateGroupDtoToBucketListGroup(updateGroupDto);

        given(bucketListGroupService.updateGroup(Mockito.any(BucketListGroup.class))).willReturn(bucketListGroup);

        String content = gson.toJson(updateGroupDto);
    }

//    //when
//    ResultActions actions = mockMvc.perform(
//            patch("/v1/bucket-groups/1")
//                    .accept(MediaType.APPLICATION_JSON)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(content)
//    );

    @Test
    @DisplayName("버킷리스트 그룹 조회")
    public void getBucketListGroup() throws Exception{
        //given
        long groupId = 1L;
//        long memberId =1L;
        BucketListGroupRequestDto.CreateGroupDto createGroupDto = BucketListGroupRequestDto.CreateGroupDto.builder()
                .memberId(1L)
                .title("sun")
                .build();
        BucketListGroup bucketListGroup = mapper.groupDtoToBucketListGroup(createGroupDto);
        given(bucketListGroupService.findBucketListGroup(Mockito.anyLong())).willReturn(bucketListGroup);

        //when
        ResultActions actions = mockMvc.perform(
                get("/v1/bucket-groups/{group-id}",groupId)
                        .accept(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        MvcResult result = actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect((jsonPath("$.data.title").value("sun")))
                .andReturn();
    }

    @Test
    @DisplayName("버킷리스트 그룹들 조회")
    public void getBucketListGroups() throws Exception{
        List<BucketListGroup> bucketListGroupList = new ArrayList<>();
        int page =1;
        int size =2;
       int totalElements = 4;
       for(int i=0;i<totalElements;i++){
           BucketListGroup bucketListGroup = new BucketListGroup();
           bucketListGroup.setBucketListGroupId((long) i);
           bucketListGroup.setTitle("sun"+i);
           bucketListGroup.setMember(new Member());
           bucketListGroupList.add(bucketListGroup);
       }
       Page<BucketListGroup> groupPage = new PageImpl<>(bucketListGroupList, PageRequest.of(page-1,size, Sort.by("bucketListGroupId").descending()),bucketListGroupList.size());

       given(bucketListGroupService.finBucketListGroups(Mockito.anyInt(),Mockito.anyInt())).willReturn(groupPage);

        ResultActions actions = mockMvc.perform(
                get("/bucket-groups")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        MvcResult result = actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageInfo.page").value(page))
                .andExpect(jsonPath("$.pageInfo.size").value(size))
                .andExpect(jsonPath("$.pageInfo.totalElements").value(totalElements))
                .andReturn();

    }
    @Test
    @DisplayName("버킷리스트그룹 삭제")
    public void deleteBucketGroup() throws Exception{
        //given
        long groupId =1L;

        doNothing().when(bucketListGroupService).deleteBucketListGroup(groupId);

        //when
        ResultActions actions = mockMvc.perform(
                delete("/v1/bucket-groups/{group-id}",groupId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        actions.andExpect(status().isNoContent());
    }
}
