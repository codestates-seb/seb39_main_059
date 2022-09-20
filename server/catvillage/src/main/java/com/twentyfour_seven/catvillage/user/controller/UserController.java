package com.twentyfour_seven.catvillage.user.controller;

import com.twentyfour_seven.catvillage.user.dto.UserPostDto;
import com.twentyfour_seven.catvillage.user.entity.User;
import com.twentyfour_seven.catvillage.user.mapper.UserMapper;
import com.twentyfour_seven.catvillage.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@Transactional
@Validated
public class UserController {
    private UserService userService;
    private UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity postUser(@Valid @RequestBody UserPostDto userPostDto) {
        User user = userMapper.userPostDtoToUser(userPostDto);
        User createUser = userService.createUser(user);
        return new ResponseEntity<>(userMapper.userToUserPostResponseDto(createUser), HttpStatus.CREATED);
    }

    @GetMapping("/names")
    public ResponseEntity getNameCheck(@RequestParam String name) {
        userService.nameDuplicateCheck(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
