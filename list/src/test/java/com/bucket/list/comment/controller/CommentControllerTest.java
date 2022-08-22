package com.bucket.list.comment.controller;

import com.bucket.list.comment.dto.CommentsRequestDto;
import com.bucket.list.comment.entity.Comments;
import com.bucket.list.comment.mapper.CommentsMapper;
import com.bucket.list.comment.service.CommentsService;
import com.bucket.list.helper.WithMockCustomUser;
import com.bucket.list.stub.comments.CommentsStub;
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
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(CommentController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@WithMockCustomUser
class CommentControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private Gson gson;

  @MockBean
  private CommentsMapper mapper;

  @MockBean
  private CommentsService commentsService;

  @MockBean
  private JwtTokenProvider jwtTokenProvider;

  @Test
  @DisplayName("댓글 달기 테스트")
  void createComments() {
    /*
    @PostMapping
  public ResponseEntity createComments(@AuthenticationPrincipal MemberDetails memberDetails,
                                       @Positive @PathVariable("completed-list-id") long completedListId,
                                       @RequestBody @Valid CommentsRequestDto.CreateCommentsDto createCommentsDto) {
    // TO-DO : MEMBER ID 이용하는 로직으로 변경
    createCommentsDto.setCompletedListId(completedListId);
    createCommentsDto.setMemberId(memberDetails.getMemberId());
    Comments comments = commentsService.createComments(mapper.createCommentsDtoToComments(createCommentsDto));
    return new ResponseEntity<>(new SingleResponseWithMessageDto<>(mapper.commentsToCommentsInfo(comments),
      "SUCCESS"),
      HttpStatus.CREATED);
  }
     */

    // given
    long completedListId = 1L;
    String contents = "ㅋㅋㅋㅋㅋ";
    CommentsRequestDto.CreateCommentsDto createCommentsDto = CommentsStub.createCommentsDto(completedListId, contents);
//    Comments comments = CommentsStub.getComments(completedListId, contents);
    String content = gson.toJson(createCommentsDto);

    given(mapper.createCommentsDtoToComments(Mockito.any(CommentsRequestDto.CreateCommentsDto.class))).willReturn(new Comments());
//    given(commentsService.createComments(Mockito.any(Comments.class))).willReturn(comments);



    // when

    // then
  }

  @Test
  void getComments() {
    /*
    @GetMapping
  public ResponseEntity getComments(@Positive @PathVariable("completed-list-id") long completedListId,
                                    @Positive @PathParam("page") int page,
                                    @Positive @PathParam("size") int size) {
    Page<Comments> pageOfComments = commentsService.findCommentsLists(completedListId, page - 1, size);
    List<Comments> commentsList = pageOfComments.getContent();
    return new ResponseEntity<>(new MultiResponseWithPageInfoDto<>(mapper.commentsListToCommentsInfoList(commentsList),
      pageOfComments),
      HttpStatus.OK);
  }
     */
  }

  @Test
  void updateComments() {
    /*
    @PatchMapping("{comments-id}")
  public ResponseEntity updateComments(@AuthenticationPrincipal MemberDetails memberDetails,
                                       @Positive @PathVariable("completed-list-id") long completedListId,
                                       @Positive @PathVariable("comments-id") long commentsId,
                                       @RequestBody @Valid CommentsRequestDto.UpdateCommentsDto updateCommentsDto) {
    updateCommentsDto.setCommentsId(commentsId);
    updateCommentsDto.setCompletedListId(completedListId);
    updateCommentsDto.setMemberId(memberDetails.getMemberId());
    Comments comments = commentsService.updateComments(mapper.updateCommentsDtoToComments(updateCommentsDto));
    return new ResponseEntity<>(new SingleResponseWithMessageDto<>(mapper.commentsToCommentsInfo(comments),
      "SUCCESS"),
      HttpStatus.OK);
  }
     */
  }

  @Test
  void deleteComments() {
    /*
    @DeleteMapping("{comments-id}")
  public ResponseEntity deleteComments(@AuthenticationPrincipal MemberDetails memberDetails,
                                       @Positive @PathVariable("completed-list-id") long completedListId,
                                       @Positive @PathVariable("comments-id") long commentsId) {
    commentsService.deleteComments(commentsId, completedListId, memberDetails.getMemberId());
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
     */
  }
}