package com.bucket.list.member.controller;

import com.bucket.list.auth.local.service.AuthService;
import com.bucket.list.dto.response.MessageResponseDto;
import com.bucket.list.dto.response.SingleResponseDto;
import com.bucket.list.helper.WithMockCustomUser;
import com.bucket.list.member.dto.MemberRequestDto;
import com.bucket.list.member.dto.MemberResponseDto;
import com.bucket.list.member.entity.Member;
import com.bucket.list.member.mapper.MemberMapper;
import com.bucket.list.member.service.MemberService;
import com.bucket.list.stub.member.MemberStub;
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
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import java.util.List;

import static com.bucket.list.util.ApiDocumentUtils.getRequestPreProcessor;
import static com.bucket.list.util.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(MemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc(addFilters = false)
@WithMockCustomUser
class MemberControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private Gson gson;

  @MockBean
  private JwtTokenProvider jwtTokenProvider;

  @MockBean
  private AuthService authService;


  @MockBean
  private MemberService memberService;

  @MockBean
  private MemberMapper mapper;

  @Test
  @DisplayName("회원 조회 테스트")
  public void getMemberTest() throws Exception {
    // given
    Member member = MemberStub.getMember();

    MemberResponseDto.MemberInfo memberInfo = MemberStub.getMemberInfo(member);

//    MemberResponseDto.MemberInfo memberInfo = MemberResponseDto.MemberInfo.builder()
//            .email(member.getEmail())
//            .introduction(member.getIntroduction())
//            .tel(member.getTel())
//            .nickName(member.getNickName())
//            .build();

//    SingleResponseDto responseDto = new SingleResponseDto(memberInfo);

    given(memberService.findMember(Mockito.anyLong())).willReturn(new Member());
    given(mapper.memberToMemberInfo(Mockito.any(Member.class))).willReturn(memberInfo);

    // when
    ResultActions actions = mockMvc.perform(
            get("/v1/members")
                    .header("Authorization", "Bearer {ACCESS_TOKEN}")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
    );

    // then
    ResultActions result = actions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.email").value(member.getEmail()))
            .andExpect(jsonPath("$.data.nickName").value(member.getNickName()))
            .andExpect(jsonPath("$.data.tel").value(member.getTel()))
            .andExpect(jsonPath("$.data.introduction").value(member.getIntroduction()))
            .andExpect(jsonPath("$.data.profileImg").value(member.getProfileImg()))
            .andDo(document(
                    "get-member",
                    getResponsePreProcessor(),
                    requestHeaders(headerWithName("Authorization").description("Bearer {AccessToken}")),
                    responseFields(
                            List.of(
                                    fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                    fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일"),
                                    fieldWithPath("data.nickName").type(JsonFieldType.STRING).description("닉네임"),
                                    fieldWithPath("data.introduction").type(JsonFieldType.STRING).description("소개글").optional(),
                                    fieldWithPath("data.tel").type(JsonFieldType.STRING).description("연락처"),
                                    fieldWithPath("data.profileImg").type(JsonFieldType.STRING).description("프로필 사진 URL").optional()
                            )
                    )
            ));
    System.out.println("result = " + result);
  }

  @Test
  @DisplayName("회원 정보 업데이트 테스트")
  public void updateMember() throws Exception {
    // given
    Member member = MemberStub.getMember();

    MemberRequestDto.UpdateDto updateDto = MemberStub.updateMember(member);
    MemberResponseDto.UpdateDto response = MemberStub.getUpdateMemberInfo(member);


    String content = gson.toJson(updateDto);

    given(mapper.updateDtoToMember(Mockito.any(MemberRequestDto.UpdateDto.class))).willReturn(member);
    given(memberService.updateMember(Mockito.any(Member.class))).willReturn(new Member());
    given(mapper.memberToUpdateDto(Mockito.any(Member.class))).willReturn(response);

    // when
    ResultActions actions = mockMvc.perform(
            patch("/v1/members/")
                    .header("Authorization", "Bearer {ACCESS_TOKEN}")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
    );

    // then
    actions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.nickName").value(response.getNickName()))
            .andExpect(jsonPath("$.data.tel").value(response.getTel()))
            .andExpect(jsonPath("$.data.introduction").value(response.getIntroduction()))
            .andExpect(jsonPath("$.data.profileImg").value(response.getProfileImg()))
            .andExpect(jsonPath("$.data.memberId").value(response.getMemberId()))
            .andExpect(jsonPath("$.message").value("SUCCESS"))
            .andDo(
                    document("update-member",
                            getRequestPreProcessor(),
                            getResponsePreProcessor(),
                            requestHeaders(headerWithName("Authorization").description("Bearer {AccessToken}")),
                            requestFields(
                                    List.of(
                                            fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자").ignored(),
                                            fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호").optional(),
                                            fieldWithPath("tel").type(JsonFieldType.STRING).description("연락처").optional(),
                                            fieldWithPath("status").type(JsonFieldType.STRING).description("회원 상태").optional(),
                                            fieldWithPath("nickName").type(JsonFieldType.STRING).description("닉네임").optional(),
                                            fieldWithPath("introduction").type(JsonFieldType.STRING).description("소개글").optional(),
                                            fieldWithPath("profileImg").type(JsonFieldType.STRING).description("프로필 이미지").optional()
                                    )
                            ),
                            responseFields(
                                    List.of(
                                            fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                            fieldWithPath("data.nickName").type(JsonFieldType.STRING).description("닉네임"),
                                            fieldWithPath("data.tel").type(JsonFieldType.STRING).description("연락처"),
                                            fieldWithPath("data.introduction").type(JsonFieldType.STRING).description("소개글").optional(),
                                            fieldWithPath("data.profileImg").type(JsonFieldType.STRING).description("프로필 이미지").optional(),
                                            fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                            fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지")
                                    )
                            )
                    )
            );
  }

  @Test
  @DisplayName("회원 탈퇴 테스트")
  public void deleteMemberTest() throws Exception {
    // given
    long memberId = 1L;

    doNothing().when(memberService).deleteMember(memberId);

    // when
    ResultActions actions = mockMvc.perform(
            delete("/v1/members")
                    .header("Authorization", "Bearer {ACCESS_TOKEN}")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
    );

    // then
    actions
            .andExpect(status().isNoContent())
            .andDo(
                    document("delete-member",
                            getRequestPreProcessor(),
                            getResponsePreProcessor(),
                            requestHeaders(headerWithName("Authorization").description("Bearer {AccessToken}"))
                    )
            );
  }
}