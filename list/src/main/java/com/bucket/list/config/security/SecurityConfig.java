package com.bucket.list.config.security;

import com.bucket.list.filter.security.JwtAuthenticationFilter;
import com.bucket.list.util.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig {
  private final JwtTokenProvider jwtTokenProvider;


  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.
      csrf().disable();
    http.httpBasic().disable()
      .authorizeRequests()
      .antMatchers("/v1/**").hasRole("ROLE_USER")
      .antMatchers("/**").permitAll()
      .and()
      .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
