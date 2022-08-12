package com.bucket.list.auth.local.controller;

import com.bucket.list.auth.local.service.AuthService;
import com.bucket.list.dto.response.MessageResponseDto;
import com.bucket.list.dto.token.TokenResponseDto;
import com.bucket.list.member.dto.MemberRequestDto;
import com.bucket.list.member.entity.Member;
import com.bucket.list.member.mapper.MemberMapper;
import com.bucket.list.member.repository.MemberRepository;
import com.bucket.list.member.service.MemberService;
import com.bucket.list.stub.member.MemberStub;
import com.bucket.list.stub.token.TokenStub;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.bucket.list.util.ApiDocumentUtils.getRequestPreProcessor;
import static com.bucket.list.util.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@ContextConfiguration
class AuthControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private Gson gson;

  @MockBean
  private MemberService memberService;

  @MockBean
  private JwtTokenProvider jwtTokenProvider;

  @MockBean
  private MemberMapper mapper;

  @MockBean
  private AuthService authService;

  @MockBean
  private PasswordEncoder passwordEncoder;

  @MockBean
  private MemberRepository memberRepository;

//  @Autowired
//  private CorsFilter corsFilter;

//  @BeforeEach
//  public void setUp() throws Exception {
//    mockMvc = MockMvcBuilders
//      .webAppContextSetup(webApplicationContext)
//      .apply(springSecurity())
//      .build();
//  }

  @Test
  @DisplayName("회원가입 테스트")
  void signUp() throws Exception {
    // given
    Member member = MemberStub.getMember();
    MemberRequestDto.SignUpDto signUpDto = MemberStub.signUpMemberDto();
    MessageResponseDto messageResponseDto = MemberStub.signUpResult();
    String content = gson.toJson(signUpDto);

    given(mapper.signUpDtoToMember(Mockito.any(MemberRequestDto.SignUpDto.class))).willReturn(member);
    given(memberService.createMember(Mockito.any(Member.class))).willReturn(member);

    // when
    ResultActions actions = mockMvc.perform(
      post("/auth/signup")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(content)
    );


    // then
    actions
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.message").value(messageResponseDto.getMessage()))
      .andDo(document(
        "member-create",
        getRequestPreProcessor(),
        getResponsePreProcessor(),
        requestFields(
          List.of(
            fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
            fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
            fieldWithPath("tel").type(JsonFieldType.STRING).description("연락처"),
            fieldWithPath("nickName").type(JsonFieldType.STRING).description("닉네임")
          )
        )
      ));
  }

  @Test
  void login() throws Exception {
    // given
    MemberRequestDto.loginDto loginDto = MemberStub.loginMemberDto();
    Member member = MemberStub.getMember();
    TokenResponseDto.Token token = TokenStub.createToken();

    String content = gson.toJson(loginDto);

    given(mapper.loginDtoToMember(Mockito.any(MemberRequestDto.loginDto.class))).willReturn(member);
    given(authService.login(Mockito.any(Member.class))).willReturn(token);

    // when
    ResultActions actions = mockMvc.perform(
      post("/auth/login")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(content)
    );

    // then
    actions
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data.grantType").value(token.getGrantType()))
      .andExpect(jsonPath("$.data.accessToken").value(token.getAccessToken()))
      .andExpect(jsonPath("$.data.refreshToken").value(token.getRefreshToken()))
      .andExpect(jsonPath("$.data.accessTokenExpiredTime").value(token.getAccessTokenExpiredTime()))
      .andExpect(jsonPath("$.data.refreshTokenExpiredTime").value(token.getRefreshTokenExpiredTime()))
      .andExpect(jsonPath("$.message").value("SUCCESS"))
      .andDo(
        document("member-login",
          getRequestPreProcessor(),
          getResponsePreProcessor(),
          requestFields(
            List.of(
              fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
              fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
            )
          ),
          responseFields(
            List.of(
              fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
              fieldWithPath("data.grantType").type(JsonFieldType.STRING).description("인가 타입"),
              fieldWithPath("data.accessToken").type(JsonFieldType.STRING).description("엑세스 토큰"),
              fieldWithPath("data.refreshToken").type(JsonFieldType.STRING).description("리프레쉬 토큰"),
              fieldWithPath("data.accessTokenExpiredTime").type(JsonFieldType.NUMBER).description("엑세스 토큰 만료 시간"),
              fieldWithPath("data.refreshTokenExpiredTime").type(JsonFieldType.NUMBER).description("리프레쉬 토큰 만료 시간"),
              fieldWithPath("message").type(JsonFieldType.STRING).description("메시지")
            )
          ))
      );

  }

  @Test
  @DisplayName("accessToken 재발급")
  void reIssue() {
    // given

    // when

    // then
  }
}