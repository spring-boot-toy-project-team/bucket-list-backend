package com.bucket.list.auth.local.service;

import com.bucket.list.dto.token.TokenDto;
import com.bucket.list.exception.BusinessLogicException;
import com.bucket.list.exception.ExceptionCode;
import com.bucket.list.member.entity.Member;
import com.bucket.list.member.repository.MemberRepository;
import com.bucket.list.util.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
  private final MemberRepository memberRepository;
  private final JwtTokenProvider jwtTokenProvider;
  private final PasswordEncoder passwordEncoder;

  public TokenDto.Token login(Member member) {
    Member findMember = findVerifiedMember(member);

    if(!passwordEncoder.matches(member.getPassword(), findMember.getPassword())) {
      throw new BusinessLogicException(ExceptionCode.PASSWORD_INCORRECT);
    }

    return jwtTokenProvider.createTokenDto(findMember);
  }

  public String getCurrentMember() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }

  @Transactional(readOnly = true)
  public Member findVerifiedMember(Member member) {
    Optional<Member> optionalMember = memberRepository.findByEmail(member.getEmail());
    return optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
  }
}
