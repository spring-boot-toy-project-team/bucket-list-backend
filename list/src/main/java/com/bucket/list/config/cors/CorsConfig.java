package com.bucket.list.config.cors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 서버가 응답할 때 json을 자바스크립트에서 처리할 수 있게 설정, 응답헤더에 헤더의 속성을 true주면 클라이언트에서 보낸 쿠키를 받을 수 있다
        config.addAllowedOrigin("*");    // 모든 ip에 응답 허용
        config.addAllowedHeader("*");   // 모든 header에 응답 허용 ,요청이 허용되는 메소드를 지정, 와일드카드(*)를 통해 모든 메소드를 허용
        config.addAllowedMethod("*");  // 모든 post, get, patch, delete 요청 허용
//        config.getMaxAge() 요청결과를 캐시할 수 있는 시간
        source.registerCorsConfiguration("/api/**", config);

        return new CorsFilter(source);
    }
}
