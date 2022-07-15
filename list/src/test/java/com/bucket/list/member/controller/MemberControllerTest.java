package com.bucket.list.member.controller;

import com.bucket.list.member.dto.MemberRequestDto;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MemberControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private Gson gson;

  @BeforeEach
  public void init() throws Exception {
    MemberRequestDto.SignUpDto signUpDto = MemberRequestDto.SignUpDto.builder()
      .email("hgd@gmail.com")
      .introduction("홍길동 입니다.")
      .nickName("홍길동")
      .password("1234")
      .tel("010-1234-5678")
      .build();
    String content = gson.toJson(signUpDto);

    // when
    ResultActions actions = mockMvc.perform(
      post("/v1/members")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(content)
    );
  }

  @Test
  @DisplayName("회원 가입 테스트")
  public void signUpTest() throws Exception {
    // given
    MemberRequestDto.SignUpDto signUpDto = MemberRequestDto.SignUpDto.builder()
      .email("hgd11@gmail.com")
      .introduction("홍길동11 입니다.")
      .nickName("홍길동11")
      .password("1234")
      .tel("010-1234-5679")
      .build();
    String content = gson.toJson(signUpDto);

    // when
    ResultActions actions = mockMvc.perform(
      post("/v1/members")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(content)
    );

    // then
    MvcResult result = actions
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.message").value("WELCOME"))
      .andReturn();

    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  @DisplayName("회원 조회 테스트")
  public void getMemberTest() throws Exception {
    // given
    long memberId = 1;

    // when
    ResultActions actions = mockMvc.perform(
      get("/v1/members/"+memberId)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
    );

    // then
    MvcResult result = actions
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data.email").value("hgd@gmail.com"))
      .andReturn();

    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  @DisplayName("회원 정보 업데이트 테스트")
  public void updateMember() throws Exception {
    // given
    long memberId = 1L;
    MemberRequestDto.UpdateDto updateDto = MemberRequestDto.UpdateDto.builder()
      .nickName("hgd")
      .introduction("hi")
      .build();
    String content = gson.toJson(updateDto);

    // when
    ResultActions actions = mockMvc.perform(
      patch("/v1/members/" + memberId)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(content)
    );

    // then
    MvcResult result = actions
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data.nickName").value("hgd"))
      .andExpect(jsonPath("$.message").value("SUCCESS"))
      .andReturn();

    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  @DisplayName("회원 탈퇴 테스트")
  public void deleteMemberTest() throws Exception {
    // given
    long memberId = 1L;

    // when
    ResultActions actions = mockMvc.perform(
      delete("/v1/members/"+memberId)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
    );

    // then
    MvcResult result = actions
      .andExpect(status().isNoContent())
      .andReturn();

    ResultActions getAction = mockMvc.perform(
      get("/v1/members/"+memberId)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
    );

    MvcResult getResult = getAction
      .andExpect(status().isNotFound())
      .andReturn();

    System.out.println(result.getResponse().getContentAsString());
    System.out.println(getResult.getResponse().getContentAsString());
  }
}