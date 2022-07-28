package com.bucket.list.auth.oauth2.user;


import com.bucket.list.auth.oauth2.user.provider.AuthProvider;
import com.bucket.list.auth.oauth2.user.provider.FacebookOAuth2UserInfo;
import com.bucket.list.auth.oauth2.user.provider.GithubOAuth2UserInfo;
import com.bucket.list.auth.oauth2.user.provider.GoogleOAuth2UserInfo;
import com.bucket.list.exception.OAuth2AuthenticationProcessingException;

import java.util.Map;

public class OAuth2UserInfoFactory {
  public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
    if(registrationId.equalsIgnoreCase(AuthProvider.google.toString())) {
      return new GoogleOAuth2UserInfo(attributes);
    } else if (registrationId.equalsIgnoreCase(AuthProvider.facebook.toString())) {
      return new FacebookOAuth2UserInfo(attributes);
    } else if (registrationId.equalsIgnoreCase(AuthProvider.github.toString())) {
      return new GithubOAuth2UserInfo(attributes);
    } else {
      throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
    }
  }
}
