package com.twentyfour_seven.catvillage.security.controller;

import com.twentyfour_seven.catvillage.security.service.AuthService;
import com.twentyfour_seven.catvillage.security.dto.TokenDto;
import com.twentyfour_seven.catvillage.security.dto.TokenRequestDto;
import com.twentyfour_seven.catvillage.security.dto.UserRequestDto;
import com.twentyfour_seven.catvillage.user.dto.UserPostDto;
import com.twentyfour_seven.catvillage.user.dto.UserPostResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequiredArgsConstructor
@Transactional
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "회원가입", description = "이미 가입되어 있는 이메일일 경우 에러가 납니다.")
    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody UserPostDto userPostDto) {
        return new ResponseEntity<>(authService.signup(userPostDto), HttpStatus.CREATED);
    }

    @Operation(summary = "로그인",
            description = "등록되어 있지 않은 유저일 경우 에러가 납니다.")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserRequestDto userRequestDto) {
        return new ResponseEntity<>(authService.login(userRequestDto), HttpStatus.OK);
    }

    @Operation(summary = "JWT 토큰 재발급",
            description = "엑세스 토큰의 기간이 만료된 경우 해당 요청으로 엑세스 토큰 + 리프레시 토큰을 새로 발급받을 수 있습니다.")
    @PostMapping("/reissue")
    public ResponseEntity reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return new ResponseEntity<>(authService.reissue(tokenRequestDto), HttpStatus.OK);
    }
}
