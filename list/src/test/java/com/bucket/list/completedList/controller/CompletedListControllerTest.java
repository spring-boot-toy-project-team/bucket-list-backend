package com.bucket.list.completedList.controller;

import com.bucket.list.comment.controller.CommentController;
import com.bucket.list.completedList.dto.CompletedListRequestDto;
import com.bucket.list.completedList.dto.CompletedListResponseDto;
import com.bucket.list.completedList.entity.CompletedList;
import com.bucket.list.completedList.mapper.CompletedListMapper;
import com.bucket.list.completedList.service.CompletedListService;
import com.bucket.list.dto.response.MessageResponseDto;
import com.bucket.list.helper.WithMockCustomUser;
import com.bucket.list.stub.completedList.CompletedListStub;
import com.bucket.list.util.security.JwtTokenProvider;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.RequestPartsSnippet;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.bucket.list.util.ApiDocumentUtils.getRequestPreProcessor;
import static com.bucket.list.util.ApiDocumentUtils.getResponsePreProcessor;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(CompletedListController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@WithMockCustomUser
class CompletedListControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private CompletedListMapper mapper;

    @MockBean
    private CompletedListService completedListService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("인증글 등록")
    void createCompletedList() throws Exception {
        // given
        long bucketListId = 1L;
        CompletedListRequestDto.CreateCompletedListDto createCompletedListDto = CompletedListStub.createCompletedListDto();
        CompletedList completedList = CompletedListStub.getCompletedList();
        CompletedListResponseDto.CompletedListInfo completedListInfo = CompletedListStub.getCompletedListInfo(completedList);
        MessageResponseDto messageResponseDto = CompletedListStub.getCompletedListResult();
        String content = gson.toJson(createCompletedListDto);
        MockMultipartFile dataJson = new MockMultipartFile("data", null, "application/json", content.getBytes(StandardCharsets.UTF_8));
        MockMultipartFile files = new MockMultipartFile("files", "test.png", "image/png", "test".getBytes(StandardCharsets.UTF_8));

        given(mapper.createCompletedListDtoToCompletedList(Mockito.any(CompletedListRequestDto.CreateCompletedListDto.class))).willReturn(completedList);
        given(completedListService.createCompletedList(Mockito.any(CompletedList.class), Mockito.anyList())).willReturn(completedList);
        given(mapper.completeListToCompletedInfo(Mockito.any(CompletedList.class))).willReturn(completedListInfo);

        // when
        ResultActions actions = mockMvc.perform(
                multipart("/v1/bucket-list/{bucket-list-id}/complete", bucketListId)
                        .file(dataJson)
                        .file(files)
                        .accept(MediaType.APPLICATION_JSON, MediaType.MULTIPART_FORM_DATA)
                        .header("Authorization", "Bearer {ACCESS_TOKEN}")
        );

        // then
        actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.contents").value(completedListInfo.getContents()))
                .andExpect(jsonPath("$.data.tags").value(completedListInfo.getTags()))
                .andExpect(jsonPath("$.data.bucketListId").value(completedListInfo.getBucketListId()))
                .andExpect(jsonPath("$.message").value(messageResponseDto.getMessage()))
                .andDo(document(
                                "completedList-create",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                pathParameters(
                                parameterWithName("bucket-list-id").description("버킷리스트 아이디")
                                ),
                                requestHeaders(headerWithName("Authorization").description("Bearer AccessToken")),
                                requestParts( partWithName("data").description("댓글 정보").optional(),
                                            partWithName("files").description("이미지 파일").optional()),


                                requestPartFields("data",List.of(
                                        fieldWithPath("contents").type(JsonFieldType.STRING).description("내용"),
                                        fieldWithPath("tags").type(JsonFieldType.STRING).description("태그"),
                                        fieldWithPath("bucketListId").type(JsonFieldType.NUMBER).description("버킷 리스트아이디").ignored(),
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 아이디").ignored()


                                )),
                                responseFields(List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.completedListId").type(JsonFieldType.NUMBER).description("완료리스트 아이디"),
                                        fieldWithPath("data.bucketListId").type(JsonFieldType.NUMBER).description("버킷리스트 아이디"),
                                        fieldWithPath("data.contents").type(JsonFieldType.STRING).description("내용"),
                                        fieldWithPath("data.tags").type(JsonFieldType.STRING).description("태그"),
                                        fieldWithPath("data.imgs[]").type(JsonFieldType.ARRAY).description("이미지"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메시지")
                                ))

                        )
                );
    }

    @Test
    @DisplayName("인증글 조회")
    void getCompletedList() throws Exception {
        //given
        long bucketListId = 1L;
        long completedListId = 1L;
        CompletedList completedList = CompletedListStub.getCompletedList();
        CompletedListResponseDto.CompletedListInfo completedListInfo = CompletedListStub.getCompletedListInfo(completedList);

        given(completedListService.findCompletedList(Mockito.anyLong(),Mockito.anyLong())).willReturn(completedList);
        given(mapper.completeListToCompletedInfo(Mockito.any(CompletedList.class))).willReturn(completedListInfo);

        //when
        ResultActions actions = mockMvc.perform(
                get("/v1/bucket-list/{bucket-list-id}/complete/{completed-list-id}",bucketListId,completedListId)
                        .accept(MediaType.APPLICATION_JSON, MediaType.MULTIPART_FORM_DATA)
                        .header("Authorization", "Bearer {ACCESS_TOKEN}")
        );

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.contents").value(completedListInfo.getContents()))
                .andExpect(jsonPath("$.data.tags").value(completedListInfo.getTags()))
                .andDo(document(
                        "completedList-get",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(headerWithName("Authorization").description("Bearer AccessToken")),
                        pathParameters(
                                parameterWithName("bucket-list-id").description("버킷리스트 아이디"),
                                parameterWithName("completed-list-id").description("완료리스트 아이디")
                        ),
                        responseFields(List.of(
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                fieldWithPath("data.completedListId").type(JsonFieldType.NUMBER).description("완료리스트 아이디"),
                                fieldWithPath("data.bucketListId").type(JsonFieldType.NUMBER).description("버킷리스트 아이디"),
                                fieldWithPath("data.contents").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("data.tags").type(JsonFieldType.STRING).description("태그"),
                                fieldWithPath("data.imgs[]").type(JsonFieldType.ARRAY).description("이미지"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("메시지")
                        ))

                        ));

    }

//    @Test
//    @DisplayName("인증글 리스트 조회")
//    void getCompletedLists() throws Exception {
//        //given
//        int page = 1, size = 3;
//        long bucketListId = 1L;
//        Page<CompletedList> completedListPage = CompletedListStub.completedListPage(page,size);
//        List<CompletedListResponseDto.CompletedListInfo> completedListInfoList = CompletedListStub.completedListInfoList(page,size);
//
//        given(completedListService.findCompletedLists(Mockito.anyLong(),Mockito.anyInt(),Mockito.anyInt())).willReturn(completedListPage);
//        given(mapper.completeListsToCompletedInfoList(Mockito.anyList())).willReturn(completedListInfoList);
//
//        //when
//        ResultActions actions = mockMvc.perform(
//                get("/v1/bucket-list/complete?page={page}&size={size}",page,size)
//                        .accept(MediaType.APPLICATION_JSON, MediaType.MULTIPART_FORM_DATA)
//                        .header("Authorization", "Bearer {ACCESS_TOKEN}")
//        );
//
//        //then
//        actions.andExpect(status().isOk())
//                .andExpect(jsonPath("$.pageInfo.page").value(page))
//                .andExpect(jsonPath("$.pageInfo.size").value(size))
//                .andDo(document(
//                        "completedLists-get",
//                        getRequestPreProcessor(),
//                        getResponsePreProcessor(),
//                        requestHeaders(headerWithName("Authorization").description("Bearer AccessToken")),
//                        requestParameters(
//                                parameterWithName("page").description("페이지 수"),
//                                parameterWithName("size").description("페이지 크기")
//                        ),
//                        responseFields(List.of(
//                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
//                                fieldWithPath("data.completedListId").type(JsonFieldType.NUMBER).description("완료리스트 아이디"),
//                                fieldWithPath("data.bucketListId").type(JsonFieldType.NUMBER).description("버킷리스트 아이디"),
//                                fieldWithPath("data.contents").type(JsonFieldType.STRING).description("내용"),
//                                fieldWithPath("data.tags").type(JsonFieldType.STRING).description("태그"),
//                                fieldWithPath("data.imgs[]").type(JsonFieldType.ARRAY).description("이미지")
//                        ))
//
//                ));
//    }

    @Test
    @DisplayName("인증글 수정")
    void updateCompletedList() throws Exception {
        //given
        long bucketListId = 1L;
        long completedListId = 1L;
        CompletedList completedList = CompletedListStub.getCompletedList();
        CompletedListRequestDto.UpdateCompletedListDto updateCompletedListDto = CompletedListStub.updateCompletedListDto(completedList);
        CompletedListResponseDto.CompletedListInfo completedListInfo = CompletedListStub.updateResponseCompletedListDto(completedList);
        String content = gson.toJson(updateCompletedListDto);
        MockMultipartFile dataJson = new MockMultipartFile("data", null, "application/json", content.getBytes(StandardCharsets.UTF_8));
        MockMultipartFile files = new MockMultipartFile("files", "test.png", "image/png", "test".getBytes(StandardCharsets.UTF_8));


        given(mapper.updateCompletedListToCompletedList(Mockito.any(CompletedListRequestDto.UpdateCompletedListDto.class))).willReturn(completedList);
        given(completedListService.updateCompletedList(Mockito.any(CompletedList.class),Mockito.anyList())).willReturn(completedList);
        given(mapper.completeListToCompletedInfo(Mockito.any(CompletedList.class))).willReturn(completedListInfo);

        //when
        ResultActions actions = mockMvc.perform(
                multipartPatchBuilder("/v1/bucket-list/{bucket-list-id}/complete/{completed-list-id}",bucketListId,completedListId)
                        .file(dataJson)
                        .file(files)
                        .accept(MediaType.APPLICATION_JSON, MediaType.MULTIPART_FORM_DATA)
                        .header("Authorization", "Bearer {ACCESS_TOKEN}")
        );

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.contents").value(completedListInfo.getContents()))
                .andExpect(jsonPath("$.data.tags").value(completedListInfo.getTags()))
                .andDo(document(
                        "completedList-update",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(headerWithName("Authorization").description("Bearer AccessToken")),
                        pathParameters(
                                parameterWithName("bucket-list-id").description("버킷리스트 아이디"),
                                parameterWithName("completed-list-id").description("완료리스트 아이디")

                        ),requestParts(
                                partWithName("data").description("댓글 정보").optional(),
                                partWithName("files").description("이미지 파일").optional()),

                        requestPartFields("data",List.of(
                                fieldWithPath("contents").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("tags").type(JsonFieldType.STRING).description("태그"),
                                fieldWithPath("bucketListId").type(JsonFieldType.NUMBER).description("버킷 리스트아이디").ignored(),
                                fieldWithPath("completedListId").type(JsonFieldType.NUMBER).description("완료리스트 아이디").ignored(),
                                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 아이디").ignored()

                        )),
                        responseFields(List.of(
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                fieldWithPath("data.completedListId").type(JsonFieldType.NUMBER).description("완료리스트 아이디"),
                                fieldWithPath("data.bucketListId").type(JsonFieldType.NUMBER).description("버킷리스트 아이디"),
                                fieldWithPath("data.contents").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("data.tags").type(JsonFieldType.STRING).description("태그"),
                                fieldWithPath("data.imgs[]").type(JsonFieldType.ARRAY).description("이미지"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("메시지")
                        ))

                ));


    }

    @Test
    @DisplayName("인증글 삭제")
    void deleteCompletedList() {
    }

    @Test
    void getCompletedListByNickName() {
    }

    @Test
    void getCompletedListByTagName() {
    }

    private MockMultipartHttpServletRequestBuilder multipartPatchBuilder(final String url,long bucketListId, long completedListId) {
        final MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart(url,bucketListId,completedListId);
        builder.with(request1 -> {
            request1.setMethod(HttpMethod.PATCH.name());
            return request1;
        });
        return builder;
}
}