package com.bucket.list.bucketList.controller;

import com.bucket.list.bucketList.mapper.BucketListMapper;
import com.bucket.list.bucketList.service.BucketListService;
import com.bucket.list.helper.WithMockCustomUser;
import com.bucket.list.util.security.JwtTokenProvider;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(BucketListGroupController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@WithMockCustomUser
class BucketListControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private Gson gson;

  @MockBean
  private BucketListMapper mapper;

  @MockBean
  private BucketListService bucketListService;

  @MockBean
  private JwtTokenProvider jwtTokenProvider;

  @Test
  void createBucketList() {
    // given

    // when

    // then
  }

  @Test
  void getBucketList() {
  }

  @Test
  void getBucketLists() {
  }

  @Test
  void deleteBucketList() {
  }

  @Test
  void updateBucketList() {
  }
}