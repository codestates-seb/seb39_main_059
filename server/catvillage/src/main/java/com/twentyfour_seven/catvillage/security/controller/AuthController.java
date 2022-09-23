package com.twentyfour_seven.catvillage.security.controller;

import com.twentyfour_seven.catvillage.security.service.AuthService;
import com.twentyfour_seven.catvillage.security.dto.TokenDto;
import com.twentyfour_seven.catvillage.security.dto.TokenRequestDto;
import com.twentyfour_seven.catvillage.security.dto.UserRequestDto;
import com.twentyfour_seven.catvillage.user.dto.UserPostDto;
import com.twentyfour_seven.catvillage.user.dto.UserPostResponseDto;
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

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody UserPostDto userPostDto) {
        return new ResponseEntity<>(authService.signup(userPostDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserRequestDto userRequestDto) {
        return new ResponseEntity<>(authService.login(userRequestDto), HttpStatus.OK);
    }

    @PostMapping("/reissue")
    public ResponseEntity reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return new ResponseEntity<>(authService.reissue(tokenRequestDto), HttpStatus.OK);
    }
}
