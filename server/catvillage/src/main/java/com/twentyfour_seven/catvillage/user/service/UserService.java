package com.twentyfour_seven.catvillage.user.service;

import com.twentyfour_seven.catvillage.exception.BusinessLogicException;
import com.twentyfour_seven.catvillage.exception.ExceptionCode;
import com.twentyfour_seven.catvillage.user.entity.User;
import com.twentyfour_seven.catvillage.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        verifiedExistedEmail(user.getEmail());
        verifiedExistedName(user.getName());
        return userRepository.save(user);
    }

    public void nameDuplicateCheck(String name) {
        verifiedExistedName(name);
    }

    private User findVerifiedUser(Long id) {
        Optional<User> findUser = userRepository.findById(id);
        return findUser.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    private User findVerifiedEmail(String email) {
        Optional<User> findUser = userRepository.findByEmail(email);
        return findUser.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    private User findVerifiedName(String name) {
        Optional<User> findUser = userRepository.findByName(name);
        return findUser.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    private void verifiedExistedEmail(String email) {
        Optional<User> findUser = userRepository.findByEmail(email);
        if (findUser.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_EMAIL_EXISTS);
        }
    }

    private void verifiedExistedName(String name) {
        Optional<User> findUser = userRepository.findByName(name);
        if (findUser.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NAME_EXISTS);
        }
    }
}
