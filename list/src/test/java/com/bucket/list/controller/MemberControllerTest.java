package com.bucket.list.controller;

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
    @DisplayName("회원 수정 테스트")
    public void updateMemberTest() throws Exception{
        //given
        long memberId = 1;
        MemberRequestDto.UpdateDto updateDto = MemberRequestDto.UpdateDto.builder()
                .introduction("홍길동33 입니다.")
                .nickName("홍길동33")
                .password("3333")
                .tel("010-3333-5678")
                .profileImg("333입니다")
                .build();
        long memberId1 = updateDto.getMemberId();
        String content = gson.toJson(updateDto);

        // when
        ResultActions actions = mockMvc.perform(
                patch("/v1/members/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        //then
        MvcResult result = actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.data.nickName").value(updateDto.getNickName()))
                .andReturn();
    }

    @Test
    @DisplayName("회원 삭제 테스트")
    public void deleteMember() throws Exception{
        //given
        long memberId =1;

        // when
        ResultActions actions = mockMvc.perform(
                delete("/v1/members/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)

        );

//        ResultActions action = mockMvc.perform(
//                get("/v1/members/1")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON)
//
//        );

        //then
        MvcResult result = actions
                .andExpect(status().isNoContent())
                .andReturn();

    }
}
