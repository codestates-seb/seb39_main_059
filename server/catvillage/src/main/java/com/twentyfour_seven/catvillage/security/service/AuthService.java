package com.twentyfour_seven.catvillage.security.service;

import com.twentyfour_seven.catvillage.exception.BusinessLogicException;
import com.twentyfour_seven.catvillage.exception.ExceptionCode;
import com.twentyfour_seven.catvillage.security.provider.JwtTokenizer;
import com.twentyfour_seven.catvillage.security.dto.TokenDto;
import com.twentyfour_seven.catvillage.security.dto.TokenRequestDto;
import com.twentyfour_seven.catvillage.security.dto.UserRequestDto;
import com.twentyfour_seven.catvillage.security.refreshToken.RefreshToken;
import com.twentyfour_seven.catvillage.security.refreshToken.RefreshTokenRepository;
import com.twentyfour_seven.catvillage.user.dto.UserPostDto;
import com.twentyfour_seven.catvillage.user.dto.UserPostResponseDto;
import com.twentyfour_seven.catvillage.user.entity.User;
import com.twentyfour_seven.catvillage.user.mapper.UserMapper;
import com.twentyfour_seven.catvillage.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenizer jwtTokenizer;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserMapper userMapper;

    // 일반 회원가입 시 password 인코딩 후 저장
    public UserPostResponseDto signup(UserPostDto userPostDto) {
        if (userRepository.findByEmail(userPostDto.getEmail()).isPresent()) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }

        User user = userMapper.userPostDtoToUser(userPostDto, passwordEncoder);
        return userMapper.userToUserPostResponseDto(userRepository.save(user));
    }

    // 로그인 시 토큰 발급
    public TokenDto login(UserRequestDto userRequestDto) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = userRequestDto.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = createToken(authentication);

        // 4. RefreshToken 저장
        RefreshToken createdRefreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(createdRefreshToken);

        // 5. 토큰 발급
        return tokenDto;
    }

    // 토큰 재발급
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {
        // 1. Refresh Token 검증
        if (!jwtTokenizer.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 인증정보 가져오기
        Authentication authentication = jwtTokenizer.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 User ID(email) 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken =
                refreshTokenRepository.findByKey(authentication.getName())
                        .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. 유저의 Refresh Token 과 저장된 Refresh Token 이 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = createToken(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return tokenDto;
    }

    public void logout(TokenRequestDto tokenRequestDto) {
        // TODO: access token은 black list table에 등록하여 더 이상 해당 토큰으로 로그인 못 하도록 처리 필요

        // Refresh token 제거
        RefreshToken findRefreshToken = refreshTokenRepository.findByValue(tokenRequestDto.getRefreshToken())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.REFRESH_TOKEN_NOT_FOUND));

        refreshTokenRepository.delete(findRefreshToken);
    }

    // 클래스 내부에서만 사용 가능한 토큰 생성하는 로직
    private TokenDto createToken(Authentication authentication) {
        String accessToken = jwtTokenizer.generateAccessToken(authentication);
        String refreshToken = jwtTokenizer.generateRefreshToken(authentication);
        TokenDto tokenDto = new TokenDto(accessToken, refreshToken);
        return tokenDto;
    }
}
