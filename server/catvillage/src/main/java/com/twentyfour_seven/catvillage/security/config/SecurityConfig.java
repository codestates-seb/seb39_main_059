package com.twentyfour_seven.catvillage.security.config;

import com.twentyfour_seven.catvillage.security.CustomOAuth2UserService;
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
    private final CustomOAuth2UserService customOAuth2UserService;

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
//                .and()
//                .formLogin()
//                .loginProcessingUrl("/login")
                .and()
                .addFilter(corsFilter)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/signup").permitAll() // ??????????????? ?????? API ??? ?????? ????????? ??????
                .antMatchers(HttpMethod.POST, "/login").permitAll() // ???????????? ?????? API ??? ?????? ????????? ??????
                .antMatchers(HttpMethod.GET, "/**").permitAll() // GET ????????? ?????? ????????? ??????
                .antMatchers("/h2/**").permitAll()
                .anyRequest().authenticated() // ????????? API ??? ?????? ?????? ??????
                .and()
                .apply(new JwtSecurityConfig(jwtTokenizer))
                // OAuth2.0 ????????? ??????
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService)
                .and()
                .and()
                .logout()
                .disable();
        return http.build();
    }
}
