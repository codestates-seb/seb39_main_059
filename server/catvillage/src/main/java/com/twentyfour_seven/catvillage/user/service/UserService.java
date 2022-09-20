package com.twentyfour_seven.catvillage.user.service;

import com.twentyfour_seven.catvillage.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
