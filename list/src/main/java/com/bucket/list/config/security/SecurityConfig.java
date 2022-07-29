package com.bucket.list.config.security;

import com.bucket.list.filter.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  private final CorsFilter corsFilter;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring().antMatchers("/h2-console/**", "/favicon.ico");
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
    http.csrf().disable();
    http.headers().frameOptions().disable();
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .formLogin().disable()
      .httpBasic().disable()   // basic auth disable
//      // TO-DO : OAuth2 로그인 관련 filter 추가가
//     .oauth2Login()
//        .authorizationEndpoint()
//        .baseUri("/oauth2/authorize")
//        .authorizationRequestRepository(cookieAuthorizationRequestRepository)
//      .and()
//        .redirectionEndpoint()
//        .baseUri("/oauth2/callback/*")
//      .and()
//        .userInfoEndpoint()
//        .userService(customOAuth2UserService)
//      .and()
//        .successHandler(authenticationSuccessHandler)
//        .failureHandler(authenticationFailureHandler)
//      .and()
      .apply(new CustomDsl())
      .and()
      .authorizeRequests()
        .antMatchers("/v1/**")
        .access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
        .antMatchers("/admin/**")
        .access("hasRole('ROLE_ADMIN')")
        .anyRequest()
        .permitAll();

    return http.build();
  }

  public class CustomDsl extends AbstractHttpConfigurer<CustomDsl, HttpSecurity> {
    @Override
    public void configure(HttpSecurity builder) throws Exception {
      AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
      builder
        .addFilter(corsFilter)
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
  }
}
