package com.bucket.list.bucketList.controller;

import com.bucket.list.bucketList.dto.BucketListRequestDto;
import com.bucket.list.bucketList.dto.BucketListResponseDto;
import com.bucket.list.bucketList.entity.BucketList;
import com.bucket.list.bucketList.mapper.BucketListMapper;
import com.bucket.list.bucketList.service.BucketListService;
import com.bucket.list.dto.response.MessageResponseDto;
import com.bucket.list.helper.WithMockCustomUser;
import com.bucket.list.stub.bucketlist.BucketListStub;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(BucketListController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@WithMockCustomUser
class BucketListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private BucketListService bucketListService;

    @MockBean
    private BucketListMapper mapper;


    @Test
    @DisplayName("버킷 리스트 등록")
    void createBucketList() throws Exception {
        //given
        long bucketListGroupId = 1L;
        BucketList bucketList = BucketListStub.getBucketList();
        BucketListRequestDto.CreateBucketListDto createBucketListDto = BucketListStub.createBucketListDto();
        BucketListResponseDto.BucketListInfo bucketListInfo = BucketListStub.bucketListInfo();
        MessageResponseDto messageResponseDto = BucketListStub.getBucketListCreateResult();
        String content = gson.toJson(createBucketListDto);

        given(mapper.createBucketListDtoToBucketList(Mockito.any(BucketListRequestDto.CreateBucketListDto.class))).willReturn(bucketList);
        given(bucketListService.createBucketList(Mockito.any(BucketList.class))).willReturn(bucketList);
        given(mapper.bucketListToBucketListInfo(Mockito.any(BucketList.class))).willReturn(bucketListInfo);

        //when
        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders.post("/v1/bucket-groups/{group-id}/list",bucketListGroupId)
                        .header("Authorization", "Bearer {ACCESS_TOKEN}")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        //then
        actions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(messageResponseDto.getMessage()))
                .andExpect(jsonPath("$.data.bucketListId").value(bucketListInfo.getBucketListId()))
                .andExpect(jsonPath("$.data.target").value(bucketListInfo.getTarget()))
                .andExpect(jsonPath("$.data.completed").value(bucketListInfo.isCompleted()))
                .andDo(document(
                        "bucketList-create",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("group-id").description("버킷 그룹아이디")
                        ),
                        requestHeaders(headerWithName("Authorization").description("Bearer AccessToken")),
                        requestFields(
                                List.of(
                                        fieldWithPath("target").type(JsonFieldType.STRING).description("버킷리스트 목표"),
                                        fieldWithPath("groupId").type(JsonFieldType.NUMBER).description("버킷 그룹아이디").ignored(),
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자").ignored()
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.bucketListId").type(JsonFieldType.NUMBER).description("버킷 리스트아이디"),
                                        fieldWithPath("data.target").type(JsonFieldType.STRING).description("버킷리스트 목표"),
                                        fieldWithPath("data.completed").type(JsonFieldType.BOOLEAN).description("버킷 리스트 완성"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메시지")
                                )
                        )

                ));
    }

    @Test
    @DisplayName("버킷리스트 조회")
    void getBucketList() throws Exception {
        //given
        long bucketListGroupId = 1L;
        BucketList bucketList = BucketListStub.getBucketList();
        BucketListResponseDto.BucketListInfo bucketListInfo = BucketListStub.bucketListInfo();
        MessageResponseDto messageResponseDto = BucketListStub.getBucketListResult();

        given(bucketListService.findBucketList(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong())).willReturn(bucketList);
        given(mapper.bucketListToBucketListInfo(Mockito.any(BucketList.class))).willReturn(bucketListInfo);

        //when

        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders.get("/v1/bucket-groups/{group-id}/list/{bucket-list-id}",bucketListGroupId,bucketList.getBucketListId())
                        .header("Authorization", "Bearer {ACCESS_TOKEN}")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(messageResponseDto.getMessage()))
                .andExpect(jsonPath("$.data.bucketListId").value(bucketListInfo.getBucketListId()))
                .andExpect(jsonPath("$.data.target").value(bucketListInfo.getTarget()))
                .andExpect(jsonPath("$.data.completed").value(bucketListInfo.isCompleted()))
                .andDo(document(
                        "bucketList-get",
                        getRequestPreProcessor(),
                        getResponsePreProcessor()
                        ,pathParameters(
                                parameterWithName("group-id").description("버킷 그룹아이디"),
                                parameterWithName("bucket-list-id").description("버킷리스트 아이디")
                        ),
                        requestHeaders(headerWithName("Authorization").description("Bearer AccessToken")),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.bucketListId").type(JsonFieldType.NUMBER).description("버킷 리스트아이디"),
                                        fieldWithPath("data.target").type(JsonFieldType.STRING).description("버킷리스트 목표"),
                                        fieldWithPath("data.completed").type(JsonFieldType.BOOLEAN).description("버킷 리스트 완성"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메시지")
                                )
                        )
                ));
    }

    @Test
    @DisplayName("버킷리스트들 조회")
    void getBucketLists() throws Exception {
        //given
        int page = 1;
        int size = 3;
        long bucketListGroupId = 1L;
        Page<BucketList> bucketListPage = BucketListStub.bucketListInfoPage(page,size);
        List<BucketListResponseDto.BucketListInfo> bucketListInfoList = BucketListStub.bucketListInfoList(page,size);

        given(bucketListService.findBucketLists(Mockito.anyLong(),Mockito.anyInt(), Mockito.anyInt(), Mockito.anyLong())).willReturn(bucketListPage);
        given(mapper.bucketListsToBucketListInfo(Mockito.anyList())).willReturn(bucketListInfoList);

        //when
        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders.get("/v1/bucket-groups/{group-id}/list?page={page}&size={size}", bucketListGroupId, page, size)
                        .header("Authorization", "Bearer {ACCESS_TOKEN}")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)

        );

        //then
        actions.andExpect(status().isOk())
                .andDo(document(
                        "bucketLists-get",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestParameters(
                                parameterWithName("page").description("페이지 수"),
                                parameterWithName("size").description("페이지 크기")

                        ),
                        requestHeaders(headerWithName("Authorization").description("Bearer AccessToken")),
                        responseFields(List.of(
                                fieldWithPath("data[]").type(JsonFieldType.ARRAY).description("결과 데이터"),
                                fieldWithPath("data[].bucketListId").type(JsonFieldType.NUMBER).description("버킷 리스트아이디"),
                                fieldWithPath("data[].target").type(JsonFieldType.STRING).description("버킷리스트 목표"),
                                fieldWithPath("data[].completed").type(JsonFieldType.BOOLEAN).description("버킷 리스트 완성"),
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
    @DisplayName("버킷리스트 삭제")
    void deleteBucketList() throws Exception {
        //given
        BucketList bucketList = BucketListStub.getBucketList();
        long bucketListGroupId = 1L;
        doNothing().when(bucketListService).deleteBucketList(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong());

        //when
        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders.delete("/v1/bucket-groups/{group-id}/list/{bucket-list-id}",bucketListGroupId,bucketList.getBucketListId())
                        .header("Authorization", "Bearer {ACCESS_TOKEN}")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        actions.andExpect(status().isNoContent())
                .andDo(document(
                        "bucketList-delete",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(headerWithName("Authorization").description("Bearer AccessToken")),
                        pathParameters(
                                parameterWithName("group-id").description("버킷 그룹아이디"),
                                parameterWithName("bucket-list-id").description("버킷리스트 아이디")
                        ))
                );
    }

    @Test
    @DisplayName("버킷리스트 업데이트")
    void updateBucketList() throws Exception {
        //given
        long bucketListGroupId = 1L;
        BucketList bucketList = BucketListStub.getBucketList();
        BucketListRequestDto.UpdateBucketListDto updateBucketListDto = BucketListStub.updateBucketListDto(bucketList);
        BucketListResponseDto.BucketListInfo updateBucketListInfo = BucketListStub.updateBucketListInfo(bucketList);
        MessageResponseDto messageResponseDto = BucketListStub.getBucketListResult();

        String content = gson.toJson(updateBucketListDto);

        given(mapper.updateBucketListDtoToBucketList(Mockito.any(BucketListRequestDto.UpdateBucketListDto.class))).willReturn(bucketList);
        given(bucketListService.updateBucketList(Mockito.any(BucketList.class))).willReturn(bucketList);
        given(mapper.bucketListToBucketListInfo(Mockito.any(BucketList.class))).willReturn(updateBucketListInfo);

        //when
        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders.patch("/v1/bucket-groups/{group-id}/list/{bucket-list-id}",bucketListGroupId,bucketList.getBucketListId())
                .header("Authorization", "Bearer {ACCESS_TOKEN}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        );

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(messageResponseDto.getMessage()))
                .andExpect(jsonPath("$.data.bucketListId").value(updateBucketListInfo.getBucketListId()))
                .andExpect(jsonPath("$.data.target").value(updateBucketListInfo.getTarget()))
                .andExpect(jsonPath("$.data.completed").value(updateBucketListInfo.isCompleted()))
                .andDo(document(
                        "bucketList-update",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("group-id").description("버킷 그룹아이디"),
                                parameterWithName("bucket-list-id").description("버킷리스트 아이디")
                        ),
                        requestHeaders(headerWithName("Authorization").description("Bearer AccessToken")),
                        requestFields(
                                List.of(
                                        fieldWithPath("target").type(JsonFieldType.STRING).description("버킷리스트 목표"),
                                        fieldWithPath("groupId").type(JsonFieldType.NUMBER).description("버킷 그룹아이디").ignored(),
                                        fieldWithPath("bucketListId").type(JsonFieldType.NUMBER).description("버킷 리스트아이디").ignored(),
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자").ignored(),
                                        fieldWithPath("completed").type(JsonFieldType.BOOLEAN).description("버킷 리스트 완성").ignored()
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.bucketListId").type(JsonFieldType.NUMBER).description("버킷 리스트아이디"),
                                        fieldWithPath("data.target").type(JsonFieldType.STRING).description("버킷리스트 목표"),
                                        fieldWithPath("data.completed").type(JsonFieldType.BOOLEAN).description("버킷 리스트 완성"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메시지")
                                )
                        )


                ));
    }
}