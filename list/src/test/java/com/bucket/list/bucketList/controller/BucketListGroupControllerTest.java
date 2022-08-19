package com.bucket.list.bucketList.controller;

import com.bucket.list.bucketList.dto.BucketListGroupRequestDto;
import com.bucket.list.bucketList.dto.BucketListGroupResponseDto;
import com.bucket.list.bucketList.entity.BucketListGroup;
import com.bucket.list.bucketList.mapper.BucketListGroupMapper;
import com.bucket.list.bucketList.service.BucketListGroupService;
import com.bucket.list.dto.response.MessageResponseDto;
import com.bucket.list.helper.WithMockCustomUser;
import com.bucket.list.stub.bucketlist.BucketListGroupStub;
import com.bucket.list.util.security.JwtTokenProvider;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.bucket.list.util.ApiDocumentUtils.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(BucketListGroupController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@WithMockCustomUser
class BucketListGroupControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private BucketListGroupService bucketListGroupService;

    @MockBean
    private BucketListGroupMapper mapper;


    @Test
    @DisplayName("버킷그룹 등록 테스트")
    void createGroupTest() throws Exception {
        //given
        BucketListGroup bucketListGroup = BucketListGroupStub.getBucketGroup();
        BucketListGroupRequestDto.CreateGroupDto createGroupDto = BucketListGroupStub.createGroupDto();
        BucketListGroupResponseDto.CreateGroupDto bucketGroupInfo = BucketListGroupStub.getCreateBucketGroupInfo();
        MessageResponseDto messageResponseDto = BucketListGroupStub.createResult();
        String content = gson.toJson(createGroupDto);

        given(mapper.createGroupDtoToBucketListGroup(Mockito.any(BucketListGroupRequestDto.CreateGroupDto.class))).willReturn(bucketListGroup);
        given(bucketListGroupService.createGroup(Mockito.any(BucketListGroup.class))).willReturn(bucketListGroup);
        given(mapper.bucketListGroupToCreateGroupDto(Mockito.any(BucketListGroup.class))).willReturn(bucketGroupInfo);

        //when
        ResultActions actions = mockMvc.perform(
                post("/v1/bucket-groups")
                        .header("Authorization", "Bearer {ACCESS_TOKEN}")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        //then
        actions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.title").value(bucketGroupInfo.getTitle()))
                .andExpect(jsonPath("$.message").value(messageResponseDto.getMessage()))
                .andDo(document(
                        "bucketListGroup-create",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(headerWithName("Authorization").description("Bearer AccessToken")),
                        requestFields(
                                List.of(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자").ignored() //왜????

                                )
                        ), responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.bucketListGroupId").type(JsonFieldType.NUMBER).description("버킷그룹 아이디"),
                                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("제목"),
                                        fieldWithPath("data.year").type(JsonFieldType.NUMBER).description("생성년도"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메시지")
                                )
                        )
                ));
    }

    @Test
    @DisplayName("버킷리스트 그룹 조회")
    void getGroupTest() throws Exception {
        //given
        long bucketListGroupId = 1L;
//    String title = "test";
        BucketListGroup bucketListGroup = BucketListGroupStub.getBucketGroup();
        BucketListGroupResponseDto.GroupInfo bucketGroupInfo = BucketListGroupStub.getBucketGroupInfo();
        MessageResponseDto messageResponseDto = BucketListGroupStub.getBucketListGroupResult();

        given(bucketListGroupService.findBucketListGroup(Mockito.anyLong(), Mockito.anyLong())).willReturn(bucketListGroup);
        given(mapper.bucketListGroupToGroupInfo(Mockito.any(BucketListGroup.class))).willReturn(bucketGroupInfo);
        //when
        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders.get("/v1/bucket-groups/{bucket-list-group-id}", bucketListGroupId)
                        .header("Authorization", "Bearer {ACCESS_TOKEN}")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value(bucketGroupInfo.getTitle()))
                .andExpect(jsonPath("$.message").value(messageResponseDto.getMessage()))
                .andDo(document(
                        "bucketListGroup-get",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("bucket-list-group-id").description("버킷 그룹아이디")
                        ),
                        requestHeaders(headerWithName("Authorization").description("Bearer AccessToken")),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("제목"),
                                        fieldWithPath("data.year").type(JsonFieldType.NUMBER).description("생성년도"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메시지")
                                )
                        )

                ));
    }

    @Test
    @DisplayName("버킷 그룹들 조회")
    void getGroupsTest() throws Exception {
        //given
        int page = 1;
        int size = 3;
        Page<BucketListGroup> bucketListGroupPage = BucketListGroupStub.getBucketListGroupPage(page, size);
        List<BucketListGroupResponseDto.GroupInfo> groupInfoList = BucketListGroupStub.groupBucketListGroupInfoList(page, size);

        given(bucketListGroupService.findBucketListGroups(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyLong())).willReturn(bucketListGroupPage);
        given(mapper.bucketListGroupsToGroupInfo(Mockito.anyList())).willReturn(groupInfoList);

        //when
        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders.get("/v1/bucket-groups?page={page}&size={size}", page, size)
                        .header("Authorization", "Bearer {ACCESS_TOKEN}")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        actions.andExpect(status().isOk())
                .andDo(document(
                        "bucketListGroupList-get",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestParameters(
                                parameterWithName("page").description("페이지 수"),
                                parameterWithName("size").description("페이지 크기")

                        ),
                        requestHeaders(headerWithName("Authorization").description("Bearer AccessToken")),
                        responseFields(
                                List.of(
                                        fieldWithPath("data[]").type(JsonFieldType.ARRAY).description("결과 데이터"),
                                        fieldWithPath("data[].title").type(JsonFieldType.STRING).description("버킷 그룹 제목"),
                                        fieldWithPath("data[].year").type(JsonFieldType.NUMBER).description("버킷 그룹 생성 년도"),
                                        fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이지 정보"),
                                        fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("현재 페이지"),
                                        fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("현재 페이지 크기"),
                                        fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("총 데이터 수"),
                                        fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("총 페이지 수")
                                )
                        )

                ));
    }

    @Test
    @DisplayName("버킷그룹 삭제")
    void deleteGroupTest() throws Exception {
        //given
        long bucketListGroupId = 1L;
        doNothing().when(bucketListGroupService).deleteBucketListGroup(Mockito.anyLong(), Mockito.anyLong());

        //when
        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders.delete("/v1/bucket-groups/{bucket-list-group-id}", bucketListGroupId)
                        .header("Authorization", "Bearer {ACCESS_TOKEN}")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        actions.andExpect(status().isNoContent())
                .andDo(document(
                        "bucketListGroup-delete",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(headerWithName("Authorization").description("Bearer AccessToken")),
                        pathParameters(
                                parameterWithName("bucket-list-group-id").description("버킷 그룹아이디")
                        ))
                );

    }

    @Test
    @DisplayName("버킷 그룹 업데이트")
    void updateGroup() throws Exception {
        //given
        long bucketListGroupId = 1L;
        BucketListGroup bucketListGroup = BucketListGroupStub.getBucketGroup();
        BucketListGroupRequestDto.UpdateGroupDto updateGroupDto = BucketListGroupStub.updateBucketListGroupDto(bucketListGroup);
        BucketListGroupResponseDto.GroupInfo updateGroupInfo = BucketListGroupStub.updateBucketListGroupInfo(bucketListGroup);
        MessageResponseDto messageResponseDto = BucketListGroupStub.getBucketListGroupResult();

        String content = gson.toJson(updateGroupDto);

        given(mapper.updateGroupDtoToBucketListGroup(Mockito.any(BucketListGroupRequestDto.UpdateGroupDto.class))).willReturn(bucketListGroup);
        given(bucketListGroupService.updateGroup(Mockito.any(BucketListGroup.class))).willReturn(bucketListGroup);
        given(mapper.bucketListGroupToGroupInfo(Mockito.any(BucketListGroup.class))).willReturn(updateGroupInfo);

        //when
        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders.patch("/v1/bucket-groups/{bucket-list-group-id}", bucketListGroupId)
                        .header("Authorization", "Bearer {ACCESS_TOKEN}")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(messageResponseDto.getMessage()))
                .andExpect(jsonPath("$.data.title").value(updateGroupDto.getTitle()))
                .andExpect(jsonPath("$.data.year").value(updateGroupInfo.getYear()))
                .andDo(document(
                                "bucketListGroup-update",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                requestHeaders(headerWithName("Authorization").description("Bearer AccessToken")),
                                pathParameters(
                                        parameterWithName("bucket-list-group-id").description("버킷 그룹아이디")
                                ),
                                requestFields(List.of(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("버킷 그룹 제목"),
                                        fieldWithPath("bucketListGroupId").type(JsonFieldType.STRING).description("버킷 그룹아이디").ignored(),
                                        fieldWithPath("memberId").type(JsonFieldType.STRING).description("회원 식별자").ignored()

                                )), responseFields(List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("제목"),
                                        fieldWithPath("data.year").type(JsonFieldType.NUMBER).description("생성년도"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메시지")
                                ))
                        )

                );
    }
}