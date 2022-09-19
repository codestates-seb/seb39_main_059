package com.twentyfour_seven.catvillage.user.controller;

import com.twentyfour_seven.catvillage.user.mapper.UserMapper;
import com.twentyfour_seven.catvillage.user.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

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
}
