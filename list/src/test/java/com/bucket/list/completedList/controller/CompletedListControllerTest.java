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
    /*
    @PostMapping(value = "/{bucket-list-id}/complete", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity createCompletedList(@AuthenticationPrincipal MemberDetails memberDetails,
                                            @Positive @PathVariable("bucket-list-id") long bucketListId,
                                            @Valid @RequestPart("data") CompletedListRequestDto.CreateCompletedListDto createCompletedListDto,
                                            @RequestPart(name = "files", required = false) List<MultipartFile> files) {
    createCompletedListDto.setBucketListId(bucketListId);
    createCompletedListDto.setMemberId(memberDetails.getMemberId());
    CompletedList completedList = completedListService.createCompletedList(mapper.createCompletedListDtoToCompletedList(createCompletedListDto), files);
    return new ResponseEntity<>(new SingleResponseWithMessageDto<>(mapper.completeListToCompletedInfo(completedList),
      "CREATED"),
      HttpStatus.CREATED);
  }
     */

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
    /*
    @GetMapping("/{bucket-list-id}/complete/{completed-list-id}")
  public ResponseEntity getCompletedList(@AuthenticationPrincipal MemberDetails memberDetails,
                                         @Positive @PathVariable("bucket-list-id") long bucketListId,
                                         @Positive @PathVariable("completed-list-id") long completedListId) {
    CompletedList completedList = completedListService.findCompletedList(bucketListId, completedListId);
    return new ResponseEntity<>(new SingleResponseWithMessageDto<>(mapper.completeListToCompletedInfo(completedList),
      "SUCCESS"),
      HttpStatus.OK);
     */
  }

  @Test
  void getCompletedLists() {
    /*
    @GetMapping("/complete")
  public ResponseEntity getCompletedLists(@AuthenticationPrincipal MemberDetails memberDetails,
                                          @Positive @PathParam("page") int page,
                                          @Positive @PathParam("size") int size) {
    Page<CompletedList> pageOfCompletedList
      = completedListService.findCompletedLists(memberDetails.getMemberId(), page - 1, size);
    List<CompletedList> completedLists = pageOfCompletedList.getContent();
    return new ResponseEntity<>(new MultiResponseWithPageInfoDto<>(mapper.completeListsToCompletedInfoList(completedLists),
      pageOfCompletedList),
      HttpStatus.OK);
  }
     */
  }

  @Test
  void updateCompletedList() {
    /*
    @PatchMapping("/{bucket-list-id}/complete/{completed-list-id}")
  public ResponseEntity updateCompletedList(@AuthenticationPrincipal MemberDetails memberDetails,
                                            @Positive @PathVariable("bucket-list-id") long bucketListId,
                                            @Positive @PathVariable("completed-list-id") long completedListId,
                                            @Valid @RequestPart("data") CompletedListRequestDto.UpdateCompletedListDto updateCompletedListDto,
                                            @RequestPart(name = "files", required = false) List<MultipartFile> files) {
    updateCompletedListDto.setCompletedListId(completedListId);
    updateCompletedListDto.setBucketListId(bucketListId);
    updateCompletedListDto.setMemberId(memberDetails.getMemberId());
    CompletedList completedList = completedListService.updateCompletedList(
      mapper.updateCompletedListToCompletedList(updateCompletedListDto),
      files);

    return new ResponseEntity<>(new SingleResponseWithMessageDto<>(mapper.completeListToCompletedInfo(completedList),
      "SUCCESS"),
      HttpStatus.OK);
  }

     */
  }

  @Test
  void deleteCompletedList() {
    /*
    @DeleteMapping("/{bucket-list-id}/complete/{completed-list-id}")
  public ResponseEntity deleteCompletedList(@AuthenticationPrincipal MemberDetails memberDetails,
                                            @Positive @PathVariable("bucket-list-id") long bucketListId,
                                            @Positive @PathVariable("completed-list-id") long completedListId) {
    completedListService.deleteCompletedList(bucketListId, completedListId, memberDetails.getMemberId());
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
     */
  }

  @Test
  void getCompletedListByNickName() {
    /*
    GetMapping("/complete/nick-name")
  public ResponseEntity getCompletedListByNickName(@PathParam("nickName") String nickName,
                                                   @Positive @PathParam("page") int page,
                                                   @Positive @PathParam("size") int size) {
    Page<CompletedList> pageOfCompletedList = completedListService.findCompletedListsByNickName(nickName, page - 1, size);
    List<CompletedList> completedLists = pageOfCompletedList.getContent();
    return new ResponseEntity<>(new MultiResponseWithPageInfoDto<>(mapper.completeListsToCompletedInfoList(completedLists),
      pageOfCompletedList),
      HttpStatus.OK);
  }
     */
  }

  @Test
  void getCompletedListByTagName() {
    /*
    @GetMapping("/complete/tag-name")
  public ResponseEntity getCompletedListByTagName(@PathParam("tagName") String tagName,
                                                  @Positive @PathParam("page") int page,
                                                  @Positive @PathParam("size") int size) {
    Page<CompletedList> pageOfCompletedList = completedListService.findCompletedListsByTagName(tagName,page-1,size);
    List<CompletedList> completedLists = pageOfCompletedList.getContent();

    return new ResponseEntity<>(new MultiResponseWithPageInfoDto<>(mapper.completeListsToCompletedInfoList(completedLists),
      pageOfCompletedList),
      HttpStatus.OK);
  }
     */
  }
}