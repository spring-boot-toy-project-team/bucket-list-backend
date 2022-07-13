//package com.bucket.list.controller;
//
//
//import com.bucket.list.ListApplication;
//import com.bucket.list.entity.member.entity.Member;
//import com.bucket.list.entity.member.dto.MemberRequestDto;
//import com.bucket.list.entity.member.repository.MemberRepository;
//import com.bucket.list.entity.member.service.MemberService;
//import com.google.gson.Gson;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@Transactional
//@AutoConfigureMockMvc
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//public class memberControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private Gson gson;
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Autowired
//    private MemberService memberService;
//
//    Member save;
//
//    @BeforeEach
//    void createRepository(){
//        // 멤버 저장
//        Member member = new Member();
//        member.setNickName("이순신");
//        member.setEmail("lee@naver.com");
//        member.setIntroduction("거북선은 내꺼다");
//        member.setPassword("12345");
//        member.setProfileImg("찰칵");
//        member.setTel("010-1111-2222");
//        Member save = memberRepository.save(member);
//    }
//
//    @Test
//    void postMemberTest() throws Exception {
//        // given
//        Member member = save;
//        memberRepository.save(member);
//        String content = gson.toJson(member);
//
//
//        // when
//        ResultActions actions =
//                mockMvc.perform(
//                        post("/v1/members")
//                                .accept(MediaType.APPLICATION_JSON)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(content)
//                );
//
//        // then
//        MvcResult result = actions
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.data.email").value(member.getEmail()))
//                .andExpect(jsonPath("$.data.name").value(member.getNickName()))
//                .andExpect(jsonPath("$.data.phone").value(member.getPassword()))
//                .andReturn();
//
//    }
//
//    @Test
//    void patchMemberTest() throws Exception{
//        //given
//        MemberRequestDto.updateMember member = new MemberRequestDto.updateMember();
//        member.setPassword("22222");
//        member.setNickName("이방원");
//        member.setTel("010-9999-8888");
//        member.setIntroduction("다 죽어");
//        member.setProfileImg("ㅎㅎㅎ");
//        member.setMemberId(member.getMemberId());
//        String content = gson.toJson(member);
//
//        // when
//        ResultActions actions =
//                mockMvc.perform(
//                        patch("/v1/members/1")
//                                .accept(MediaType.APPLICATION_JSON)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(content)
//                );
//
//        // then
//        MvcResult result = actions
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.data.email").value(member.getIntroduction()))
//                .andExpect(jsonPath("$.data.name").value(member.getNickName()))
//                .andExpect(jsonPath("$.data.phone").value(member.getPassword()))
//                .andReturn();
//    }
//
//    @Test
//    void getMemberTest() throws Exception {
//        //given
//        Member findMember = memberService.findMembers(save.getMemberId());
//
//        // when
//        ResultActions actions =
//                mockMvc.perform(
//                        get("/v11/members/1")
//                                .accept(MediaType.APPLICATION_JSON)
//                                .contentType(MediaType.APPLICATION_JSON)
//                );
//
//        // then
//        MvcResult result = actions
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.email").value(findMember.getEmail()))
//                .andExpect(jsonPath("$.data.name").value(findMember.getPassword()))
//                .andExpect(jsonPath("$.data.phone").value(findMember.getNickName()))
//                .andReturn();
//    }
//
//    @Test
//    void getMembersTest() throws Exception {
//
//        //given
//        Member member2 = new Member();
//        member2.setNickName("김유신");
//        member2.setEmail("kys@naver.com");
//        member2.setIntroduction("아아아");
//        member2.setPassword("9999");
//        member2.setProfileImg("가자");
//        member2.setTel("010-8888-0000");
//        Member save = memberRepository.save(member2);
//
//
//
//        Member findMember = memberService.findMembers(member2.getMemberId());
//        List<Member> all = memberRepository.findAll();
//
//
//        // when
//        ResultActions actions =
//                mockMvc.perform(
//                        get("/v1/members")
//                                .accept(MediaType.APPLICATION_JSON)
//                                .contentType(MediaType.APPLICATION_JSON)
//                );
//
//        // then
//        MvcResult result = actions
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data[0].name").value(all.get(0).getNickName()))
//                .andExpect(jsonPath("$.data[1].name").value(all.get(1).getEmail()))
//                .andReturn();
//
//    }
//
//    @Test
//    void deleteMemberTest() throws Exception {
//        //given
//        Member findMember = memberService.findMembers(save.getMemberId());
//
//
//        // when
//        ResultActions actions =
//                mockMvc.perform(
//                        delete("/v1/members/{member-id}")
//                                .accept(MediaType.APPLICATION_JSON)
//                                .contentType(MediaType.APPLICATION_JSON)
//                );
//
//        // then
//        MvcResult result = actions
//                .andExpect(status().isNoContent())
//                .andReturn();
//        Assertions.assertThat(memberRepository.findById(1L).isEmpty());
//    }
//}
