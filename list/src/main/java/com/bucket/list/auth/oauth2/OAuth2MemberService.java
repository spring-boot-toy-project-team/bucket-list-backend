package com.bucket.list.auth.oauth2;

import com.bucket.list.auth.MemberDetails;
import com.bucket.list.auth.oauth2.user.OAuth2UserInfo;
import com.bucket.list.auth.oauth2.user.OAuth2UserInfoFactory;
import com.bucket.list.exception.security.OAuth2AuthenticationProcessingException;
import com.bucket.list.member.entity.Member;
import com.bucket.list.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.StringUtils;

import java.util.Optional;


@RequiredArgsConstructor
public class OAuth2MemberService extends DefaultOAuth2UserService {
  private final MemberRepository memberRepository;

  // OAuth2UserRequest에 있는 Access Token으로 유저정보 get
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(userRequest);
    try {
      return process(userRequest, oAuth2User);
    } catch (Exception e) {
      throw new InternalAuthenticationServiceException(e.getMessage());
    }
  }

  // 획득한 유저정보를 Java Model과 맵핑하고 프로세스 진행
  private OAuth2User process(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
    OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());

    if(StringUtils.hasText(oAuth2UserInfo.getEmail())) {
      throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
    }
    Optional<Member> optionalMember = memberRepository.findByEmail(oAuth2UserInfo.getEmail());
    Member member;

    if(optionalMember.isPresent()) {
      member = optionalMember.get();
      if(!member.getProvider().equals(oAuth2UserRequest.getClientRegistration().getRegistrationId().toLowerCase())) {
        throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
          member.getProvider() + " account. Please use your " + member.getProvider() +
          " account to login.");
      }
      member = updateExistingMember(member, oAuth2UserRequest);
    } else {
      member = registerNewMember(oAuth2UserRequest, oAuth2UserInfo);
    }
    return MemberDetails.create(member, oAuth2User.getAttributes());
  }

  private Member registerNewMember(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
    Member member = new Member();
    member.setProvider(oAuth2UserRequest.getClientRegistration().getRegistrationId().toLowerCase());
    member.setNickName(oAuth2UserInfo.getName());
    member.setEmail(oAuth2UserInfo.getEmail());
    member.setProfileImg(oAuth2UserInfo.getImageUrl());
    return memberRepository.save(member);
  }

  private Member updateExistingMember(Member member, OAuth2UserRequest oAuth2UserRequest) {
    member.setProvider(oAuth2UserRequest.getClientRegistration().getRegistrationId().toLowerCase());
    return memberRepository.save(member);
  }
}
