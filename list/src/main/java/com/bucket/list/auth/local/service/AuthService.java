package com.bucket.list.auth.local.service;

import com.bucket.list.dto.token.TokenRequestDto;
import com.bucket.list.dto.token.TokenResponseDto;
import com.bucket.list.exception.BusinessLogicException;
import com.bucket.list.exception.ExceptionCode;
import com.bucket.list.member.entity.Member;
import com.bucket.list.member.repository.MemberRepository;
import com.bucket.list.util.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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

  public TokenResponseDto.Token login(Member member) {
    Member findMember = findVerifiedMemberByEmail(member.getEmail());

    if(!passwordEncoder.matches(member.getPassword(), findMember.getPassword())) {
      throw new BusinessLogicException(ExceptionCode.PASSWORD_INCORRECT);
    }


    TokenResponseDto.Token token = jwtTokenProvider.createTokenDto(findMember);
    redisTemplate.opsForValue()
            .set(findMember.getEmail(), token.getRefreshToken(), token.getRefreshTokenExpiredTime(), TimeUnit.MILLISECONDS);

    return token;
  }

  public String getCurrentMember() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }

  public TokenResponseDto.ReIssueToken reIssue(TokenRequestDto.ReIssue reIssue) {
    Authentication authentication = jwtTokenProvider.getAuthentication(reIssue.getAccessToken());
    String redisRefreshToken = (String) redisTemplate.opsForValue().get(authentication.getName());

    if (StringUtils.hasText(redisRefreshToken)) {
      if (redisRefreshToken.equals(reIssue.getRefreshToken()))
        return jwtTokenProvider.createReIssueTokenDto(findVerifiedMemberByEmail(authentication.getName()));
      else
        throw new BusinessLogicException(ExceptionCode.TOKEN_IS_NOT_VALID);

    } else {
      throw new BusinessLogicException(ExceptionCode.REFRESH_TOKEN_IS_EXPIRED);
    }
  }

  @Transactional(readOnly = true)
  public Member findVerifiedMemberByEmail(String email) {
    Optional<Member> optionalMember = memberRepository.findByEmail(email);
    return optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
  }


}
