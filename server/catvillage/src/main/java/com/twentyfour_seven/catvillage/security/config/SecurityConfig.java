package com.twentyfour_seven.catvillage.security.config;

import com.twentyfour_seven.catvillage.security.filter.JwtAccessDeniedHandler;
import com.twentyfour_seven.catvillage.security.filter.JwtAuthenticationEntryPoint;
import com.twentyfour_seven.catvillage.security.provider.JwtTokenizer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final CorsFilter corsFilter;
    private final JwtTokenizer jwtTokenizer;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()
                // TODO: formLogin 관련 설정 필요
                .and()
                .addFilter(corsFilter)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/signup").permitAll() // 회원가입을 위한 API 는 토큰 없이도 허용
                .antMatchers(HttpMethod.POST, "/login").permitAll() // 로그인을 위한 API 는 토큰 없이도 허용
                .antMatchers(HttpMethod.GET, "/**").permitAll() // GET 요청은 토큰 없이도 허용
                .antMatchers("/h2/**").permitAll()
                .anyRequest().authenticated() // 나머지 API 는 모두 인증 필요
                .and()
                .apply(new JwtSecurityConfig(jwtTokenizer));
        return http.build();
    }
}
