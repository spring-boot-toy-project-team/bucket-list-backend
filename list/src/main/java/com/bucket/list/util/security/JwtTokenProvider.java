package com.bucket.list.util.security;

import com.bucket.list.dto.token.TokenDto;
import com.bucket.list.exception.BusinessLogicException;
import com.bucket.list.exception.ExceptionCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.Base64UrlCodec;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
  @Value("spring.jwt.secret")
  private String secretKey;
  @Value("spring.jwt.type")
  private String grantType;
  @Value("spring.jwt.accessTokenExpirationDateInMs")
  private Long accessTokenExpiredTime;
  @Value("spring.jwt.refreshTokenExpirationDateInMs")
  private Long refreshTokenExpiredTime;

  private final String ROLES = "roles";
  private final UserDetailsService userDetailsService;

  @PostConstruct
  protected void init() {
    secretKey = Base64UrlCodec.BASE64URL.encode(secretKey.getBytes(StandardCharsets.UTF_8));
  }

  public TokenDto.Token createTokenDto(Long memberId, List<String> roles) {
    // TO-DO : memberId로 할지, email로 jwt에 넣을지 고민
    Claims claims = Jwts.claims().setSubject(String.valueOf(memberId));
    claims.put(ROLES, roles);

    Date now = new Date();

    String accessToken = Jwts.builder()
      .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
      .setClaims(claims)
      .setIssuedAt(now)
      .setExpiration(new Date(now.getTime() + accessTokenExpiredTime))
      .signWith(SignatureAlgorithm.HS256, secretKey)
      .compact();

    String refreshToken = Jwts.builder()
      .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
      .setExpiration(new Date(now.getTime() + refreshTokenExpiredTime))
      .signWith(SignatureAlgorithm.HS256, secretKey)
      .compact();

    return TokenDto.Token.builder()
      .grantType(grantType)
      .accessToken(accessToken)
      .accessTokenExpiredTime(accessTokenExpiredTime)
      .refreshToken(refreshToken)
      .refreshTokenExpiredTime(refreshTokenExpiredTime)
      .build();
  }

  // jwt로 인증정보를 조회
  public Authentication getAuthentication(String token) {
    // claims 추출
    Claims claims = parseClaims(token);

    // 권한 정보 없으면
    if(claims.get(ROLES) == null)
      throw new BusinessLogicException(ExceptionCode.ROLE_IS_NOT_EXISTS);
    UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject());
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  // Jwt 토큰 복호화해서 가져오기
  private Claims parseClaims(String token) {
    try {
      return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    } catch (ExpiredJwtException e) {
      return e.getClaims();
    }
  }

  // HTTP Request 의 Header 에서 Token Parsing -> "X-AUTH-TOKEN: jwt"
  public String resolveToken(HttpServletRequest request) {
    return request.getHeader("X-AUTH-TOKEN");
  }

  // jwt 의 유효성 및 만료일자 확인
  public boolean validationToken(String token) {
    try {
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
      return true;
    } catch (SecurityException | MalformedJwtException e) {
      log.error("잘못된 Jwt 서명입니다.");
    } catch (ExpiredJwtException e) {
      log.error("만료된 토큰입니다.");
    } catch (UnsupportedJwtException e) {
      log.error("지원하지 않는 토큰입니다.");
    } catch (IllegalArgumentException e) {
      log.error("잘못된 토큰입니다.");
    }
    return false;
  }
}
