package com.bucket.list.bucketList.controller;

import com.bucket.list.bucketList.dto.BucketListGroupRequestDto;
import com.bucket.list.bucketList.dto.BucketListGroupResponseDto;
import com.bucket.list.bucketList.entity.BucketListGroup;
import com.bucket.list.bucketList.mapper.BucketListGroupMapper;
import com.bucket.list.bucketList.service.BucketListGroupService;
import com.bucket.list.helper.WithMockCustomUser;
import com.bucket.list.stub.bucektlist.BucketListGroupStub;
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
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.bucket.list.util.ApiDocumentUtils.getRequestPreProcessor;
import static com.bucket.list.util.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
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
  private BucketListGroupMapper mapper;

  @MockBean
  private BucketListGroupService bucketListGroupService;

  @MockBean
  private JwtTokenProvider jwtTokenProvider;

  @Test
  @DisplayName("버킷 그룹 등록")
  void createGroup() throws Exception {
    // given
    String title = "test";
    BucketListGroupRequestDto.CreateGroupDto createGroupDto = BucketListGroupStub.createBucketListGroupDto(title);
    BucketListGroup bucketListGroup = BucketListGroupStub.getBucketListGroup(title, 1L);
    BucketListGroupResponseDto.CreateGroupDto bucketListGroupInfo = BucketListGroupStub.getCreateBucketListGroupInfo(title, 1L);
    String content = gson.toJson(createGroupDto);

    given(mapper.createGroupDtoToBucketListGroup(Mockito.any(BucketListGroupRequestDto.CreateGroupDto.class))).willReturn(new BucketListGroup());
    given(bucketListGroupService.createGroup(Mockito.any(BucketListGroup.class))).willReturn(bucketListGroup);
    given(mapper.bucketListGroupToCreateGroupDto(Mockito.any(BucketListGroup.class))).willReturn(bucketListGroupInfo);

    // when
    ResultActions actions = mockMvc.perform(
      post("/v1/bucket-groups")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer {ACCESS_TOKEN}")
        .content(content)
    );

    // then
    actions
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.data.bucketListGroupId").value(bucketListGroupInfo.getBucketListGroupId()))
      .andExpect(jsonPath("$.data.title").value(bucketListGroupInfo.getTitle()))
      .andExpect(jsonPath("$.data.year").value(bucketListGroupInfo.getYear()))
      .andExpect(jsonPath("$.message").value("CREATED"))
      .andDo(document("bucketListGroup-create",
        getRequestPreProcessor(),
        getResponsePreProcessor(),
        requestHeaders(headerWithName("Authorization").description("Bearer AccessToken")),
        requestFields(
          List.of(
            fieldWithPath("title").type(JsonFieldType.STRING).description("버킷 그룹 제목"),
            fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자").ignored()
          )
        ),
        responseFields(
          List.of(
            fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
            fieldWithPath("data.bucketListGroupId").type(JsonFieldType.NUMBER).description("버킷 그룹 식별자"),
            fieldWithPath("data.title").type(JsonFieldType.STRING).description("버킷 그룹 제목"),
            fieldWithPath("data.year").type(JsonFieldType.NUMBER).description("버킷 그룹 생성 년도"),
            fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지")
          )
        ))
      );
  }

  @Test
  @DisplayName("버킷 그룹 조회")
  void getGroup() throws Exception {
    // given
    long bucketListGroupId = 1L;
    String title = "test";
    BucketListGroup bucketListGroup = BucketListGroupStub.getBucketListGroup(title, bucketListGroupId);
    BucketListGroupResponseDto.GroupInfo bucketListGroupInfo = BucketListGroupStub.getBucketListGroupInfo(title);
    given(bucketListGroupService.findBucketListGroup(Mockito.anyLong(), Mockito.anyLong())).willReturn(bucketListGroup);
    given(mapper.bucketListGroupToGroupInfo(Mockito.any(BucketListGroup.class))).willReturn(bucketListGroupInfo);

    // when
    ResultActions actions = mockMvc.perform(
      get("/v1/bucket-groups/{bucket-list-group-id}", bucketListGroupId)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer {ACCESS_TOKEN}")
    );

    // then
    actions
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data.title").value(bucketListGroupInfo.getTitle()))
      .andExpect(jsonPath("$.data.year").value(bucketListGroupInfo.getYear()))
      .andExpect(jsonPath("$.message").value("SUCCESS"))
      .andDo(document("bucketListGroup-get",
        getResponsePreProcessor(),
        pathParameters(
            parameterWithName("bucket-list-group-id").description("버킷 그룹 식별자")
        ),
        requestHeaders(headerWithName("Authorization").description("Bearer AccessToken")),
        responseFields(
          List.of(
            fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
            fieldWithPath("data.title").type(JsonFieldType.STRING).description("버킷 그룹 제목"),
            fieldWithPath("data.year").type(JsonFieldType.NUMBER).description("버킷 그룹 생성 년도"),
            fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지")
          )
        ))
      );
  }

  @Test
  @DisplayName("버킷 그룹들 조회")
  void getGroups() throws Exception {
    // given
    int page = 1;
    int size = 5;
    Page<BucketListGroup> bucketListGroups = BucketListGroupStub.getBucketListGroups(page, size);
    List<BucketListGroupResponseDto.GroupInfo> response = BucketListGroupStub.getBucketListGroupsResponse(page, size);
    given(bucketListGroupService.findBucketListGroups(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyLong())).willReturn(bucketListGroups);
    given(mapper.bucketListGroupsToGroupInfo(Mockito.anyList())).willReturn(response);

    // when
    ResultActions actions = mockMvc.perform(
      get("/v1/bucket-groups?page={page}&size={size}", page, size)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer {ACCESS_TOKEN}")
    );

    // then
    actions
      .andExpect(status().isOk())
      .andDo(document(
        "bucketListGroups-get",
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
  @DisplayName("버킷 그룹 삭제")
  void deleteGroup() throws Exception {
    // given
    long bucketListGroupId = 1L;
    doNothing().when(bucketListGroupService).deleteBucketListGroup(Mockito.anyLong(), Mockito.anyLong());

    // when
    ResultActions actions = mockMvc.perform(
      delete("/v1/bucket-groups/{bucket-list-group-id}", bucketListGroupId)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer {ACCESS_TOKEN}")
    );

    // then
    actions
      .andExpect(status().isNoContent())
      .andDo(document(
        "bucketListGroup-delete",
        getRequestPreProcessor(),
        getResponsePreProcessor(),
        requestHeaders(headerWithName("Authorization").description("Bearer AccessToken")),
        pathParameters(
          parameterWithName("bucket-list-group-id").description("버킷 그룹 식별자")
        )
      ));
  }

  @Test
  @DisplayName("버킷 그룹 변경")
  void updateGroup() throws Exception {
    // given
    long bucketListGroupId = 1L;
    BucketListGroup bucketListGroup = BucketListGroupStub.getBucketListGroup("test", bucketListGroupId);
    BucketListGroupRequestDto.UpdateGroupDto updateGroupDto = BucketListGroupStub.updateBucketListGroup(bucketListGroup,
      "change");
    BucketListGroupResponseDto.GroupInfo response = BucketListGroupStub.getChangedBucketListGroup(bucketListGroup, "change");
    String content = gson.toJson(updateGroupDto);

    given(mapper.updateGroupDtoToBucketListGroup(Mockito.any(BucketListGroupRequestDto.UpdateGroupDto.class))).willReturn(bucketListGroup);
    given(bucketListGroupService.updateGroup(Mockito.any(BucketListGroup.class))).willReturn(bucketListGroup);
    given(mapper.bucketListGroupToGroupInfo(Mockito.any(BucketListGroup.class))).willReturn(response);
    // when
    ResultActions actions = mockMvc.perform(
      patch("/v1/bucket-groups/{bucket-list-group-id}", bucketListGroupId)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer {ACCESS_TOKEN}")
        .content(content)
    );

    // then
    actions
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data.title").value(updateGroupDto.getTitle()))
      .andExpect(jsonPath("$.data.year").value(bucketListGroup.getYear()))
      .andExpect(jsonPath("$.message").value("SUCCESS"))
      .andDo(document(
        "bucketListGroup-update",
        getRequestPreProcessor(),
        getResponsePreProcessor(),
        requestHeaders(headerWithName("Authorization").description("Bearer AccessToken")),
        requestFields(
          List.of(
            fieldWithPath("title").type(JsonFieldType.STRING).description("버킷 그룹 제목"),
            fieldWithPath("bucketListGroupId").type(JsonFieldType.NUMBER).description("버킷 그룹 식별자").ignored(),
            fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자").ignored()
          )
        ),
        responseFields(
          List.of(
            fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
            fieldWithPath("data.title").type(JsonFieldType.STRING).description("버킷 그룹 제목"),
            fieldWithPath("data.year").type(JsonFieldType.NUMBER).description("버킷 그룹 생성 년도"),
            fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지")
          )
        )
      ));
  }
}