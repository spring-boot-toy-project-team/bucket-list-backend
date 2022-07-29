package com.bucket.list.auth.local.controller;

import com.bucket.list.auth.local.service.AuthService;
import com.bucket.list.auth.oauth2.user.provider.AuthProvider;
import com.bucket.list.dto.response.MessageResponseDto;
import com.bucket.list.dto.response.SingleResponseWithMessageDto;
import com.bucket.list.dto.token.TokenDto;
import com.bucket.list.member.dto.MemberRequestDto;
import com.bucket.list.member.entity.Member;
import com.bucket.list.member.entity.Roles;
import com.bucket.list.member.mapper.MemberMapper;
import com.bucket.list.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
  private final MemberMapper mapper;
  private final MemberService memberService;
  private final AuthService authService;
  private final PasswordEncoder passwordEncoder;

  @PostMapping("/signup")
  public ResponseEntity signUp(@RequestBody @Valid MemberRequestDto.SignUpDto signUpDto) {
    signUpDto.setProvider(AuthProvider.local.toString());
    signUpDto.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
    Member member = mapper.signUpDtoToMember(signUpDto);
    memberService.createMember(member);

    MessageResponseDto message = MessageResponseDto.builder()
      .message("WELCOME")
      .build();

    return new ResponseEntity<>(message, HttpStatus.CREATED);
  }

  @PostMapping("/login")
  public ResponseEntity login(@RequestBody @Valid MemberRequestDto.loginDto loginDto) {
    TokenDto.Token response = authService.login(mapper.loginDtoToMember(loginDto));
    return new ResponseEntity<>(new SingleResponseWithMessageDto<>(response,
      "SUCCESS"),
      HttpStatus.OK);
  }

  @PostMapping("/reissue")
  public ResponseEntity reIssue(@RequestBody @Valid TokenDto.ReIssue reIssue) {
    TokenDto.Token response =  authService.reIssue(reIssue);
    return new ResponseEntity<>(new SingleResponseWithMessageDto<>(response,
      "SUCCESS"),
      HttpStatus.OK);
  }
}
