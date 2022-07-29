package com.bucket.list.auth.local.service;

import com.bucket.list.dto.token.TokenDto;
import com.bucket.list.exception.BusinessLogicException;
import com.bucket.list.exception.ExceptionCode;
import com.bucket.list.member.entity.Member;
import com.bucket.list.member.repository.MemberRepository;
import com.bucket.list.util.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
  private final MemberRepository memberRepository;
  private final JwtTokenProvider jwtTokenProvider;
  private final PasswordEncoder passwordEncoder;
  private final RedisTemplate<String, Object> redisTemplate;

  public TokenDto.Token login(Member member) {
    Member findMember = findVerifiedMember(member);

    if(!passwordEncoder.matches(member.getPassword(), findMember.getPassword())) {
      throw new BusinessLogicException(ExceptionCode.PASSWORD_INCORRECT);
    }
    TokenDto.Token token = jwtTokenProvider.createTokenDto(findMember);
    redisTemplate.opsForValue()
      .set(findMember.getEmail(), token.getRefreshToken(), token.getRefreshTokenExpiredTime(), TimeUnit.MILLISECONDS);
    return token;
  }

  public String getCurrentMember() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }

  public TokenDto.Token reIssue(TokenDto.ReIssue reIssue) {
    return null;
  }

  @Transactional(readOnly = true)
  public Member findVerifiedMember(Member member) {
    Optional<Member> optionalMember = memberRepository.findByEmail(member.getEmail());
    return optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
  }


}
