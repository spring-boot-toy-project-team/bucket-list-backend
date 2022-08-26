package com.bucket.list.completedList.controller;

import com.bucket.list.comment.controller.CommentController;
import com.bucket.list.completedList.dto.CompletedListRequestDto;
import com.bucket.list.completedList.dto.CompletedListResponseDto;
import com.bucket.list.completedList.entity.CompletedList;
import com.bucket.list.completedList.mapper.CompletedListMapper;
import com.bucket.list.completedList.service.CompletedListService;
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
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import static com.bucket.list.util.ApiDocumentUtils.getRequestPreProcessor;
import static com.bucket.list.util.ApiDocumentUtils.getResponsePreProcessor;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
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
        String contents = "test";
        String tags = "#test1#test2#test3";
        CompletedListRequestDto.CreateCompletedListDto createCompletedListDto = CompletedListStub.createCompletedListDto(contents, tags);
        CompletedList completedList = CompletedListStub.getCompletedList(contents, tags);
        CompletedListResponseDto.CompletedListInfo completedListInfo = CompletedListStub.getCompletedListInfo(completedList);
        String content = gson.toJson(createCompletedListDto);
        MockMultipartFile dataJson = new MockMultipartFile("data", null,
                "application/json", content.getBytes());
        MockMultipartFile files = new MockMultipartFile("files", "test.png", "image/png",
                "test".getBytes());

        given(mapper.createCompletedListDtoToCompletedList(Mockito.any(CompletedListRequestDto.CreateCompletedListDto.class))).willReturn(completedList);
        given(completedListService.createCompletedList(Mockito.any(CompletedList.class), Mockito.anyList())).willReturn(completedList);
        given(mapper.completeListToCompletedInfo(Mockito.any(CompletedList.class))).willReturn(completedListInfo);

        // when
        ResultActions actions = mockMvc.perform(
                multipart("/v1/bucket-list/{bucket-list-id}/complete", bucketListId)
                        .file(dataJson)
                        .file(files)
                        .header("Authorization", "Bearer {ACCESS_TOKEN}")
        );

        System.out.println(actions.andReturn().getResponse().getContentAsString());

        // then
        actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.contents").value(completedListInfo.getContents()))
                .andExpect(jsonPath("$.data.tags").value(completedListInfo.getTags()))
                .andExpect(jsonPath("$.data.bucketListId").value(completedListInfo.getBucketListId()))
                .andExpect(jsonPath("$.message").value("CREATED"))
                .andDo(
                        document(
                                "completedList-create",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                requestHeaders(headerWithName("Authorization").description("Bearer AccessToken")),

                                responseFields()

                        )
                );
    }

    @Test
    void getCompletedList() {
    }

    @Test
    void getCompletedLists() {
    }

    @Test
    void updateCompletedList() {
    }

    @Test
    void deleteCompletedList() {
    }

    @Test
    void getCompletedListByNickName() {
    }

    @Test
    void getCompletedListByTagName() {
    }
}