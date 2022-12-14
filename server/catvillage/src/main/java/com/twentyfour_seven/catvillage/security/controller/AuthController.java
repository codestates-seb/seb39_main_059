package com.twentyfour_seven.catvillage.security.controller;

import com.twentyfour_seven.catvillage.security.dto.TokenDto;
import com.twentyfour_seven.catvillage.security.dto.TokenRequestDto;
import com.twentyfour_seven.catvillage.security.dto.UserRequestDto;
import com.twentyfour_seven.catvillage.security.service.AuthService;
import com.twentyfour_seven.catvillage.user.dto.UserPostDto;
import com.twentyfour_seven.catvillage.user.dto.UserPostResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

//@Tag(name = "Auth", description = "인증 API")
@RestController
@RequiredArgsConstructor
@Transactional
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "회원가입", description = "이미 가입되어 있는 이메일일 경우 에러가 납니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "회원가입 성공", content = @Content(schema = @Schema(implementation = UserPostResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "이미 존재하는 이메일")
            })
    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody UserPostDto userPostDto) {
        return new ResponseEntity<>(authService.signup(userPostDto), HttpStatus.CREATED);
    }

    @Operation(summary = "로그인",
            description = "등록되어 있지 않은 유저일 경우 에러가 납니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(schema = @Schema(implementation = TokenDto.class))),
                    @ApiResponse(responseCode = "400", description = "인증 실패")
            })
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserRequestDto userRequestDto) {
        return new ResponseEntity<>(authService.login(userRequestDto), HttpStatus.OK);
    }

    @Operation(summary = "JWT 토큰 재발급",
            description = "엑세스 토큰의 기간이 만료된 경우 해당 요청으로 엑세스 토큰 + 리프레시 토큰을 새로 발급받을 수 있습니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "토큰 재발급 성공", content = @Content(schema = @Schema(implementation = TokenDto.class))),
                    @ApiResponse(responseCode = "400", description = "토근 재발급 실패")
            })
    @PostMapping("/reissue")
    public ResponseEntity reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return new ResponseEntity<>(authService.reissue(tokenRequestDto), HttpStatus.OK);
    }

    @Operation(summary = "로그아웃",
            responses = {
                    @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 Refresh token")
            }
    )
    @PostMapping("/logout")
    public ResponseEntity logout(@RequestBody TokenRequestDto tokenRequestDto,
                                 @AuthenticationPrincipal User user) {
        if (user != null) {
            authService.logout(tokenRequestDto);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
