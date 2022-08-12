package com.bucket.list.helper;

import com.bucket.list.auth.MemberDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

  @Override
  public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
    SecurityContext context = SecurityContextHolder.createEmptyContext();
    Collection<GrantedAuthority> authorities = new ArrayList<>();
    List<String> roleList = new ArrayList<>(List.of("ROLE_USER"));
    roleList.forEach(n -> {
      authorities.add(() -> n);
    });
    MemberDetails principal = new MemberDetails(customUser.memberId(), customUser.email(), customUser.nickName(), authorities);
    Authentication auth =
      new UsernamePasswordAuthenticationToken(principal, customUser.password(), principal.getAuthorities());
    context.setAuthentication(auth);
    return context;
  }
}
