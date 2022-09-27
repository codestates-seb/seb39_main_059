package com.twentyfour_seven.catvillage.user.service;

import com.twentyfour_seven.catvillage.exception.BusinessLogicException;
import com.twentyfour_seven.catvillage.exception.ExceptionCode;
import com.twentyfour_seven.catvillage.user.entity.User;
import com.twentyfour_seven.catvillage.user.repository.UserRepository;
import com.twentyfour_seven.catvillage.utils.CustomBeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private CustomBeanUtils<User> beanUtils;

    public UserService(UserRepository userRepository, CustomBeanUtils<User> beanUtils) {
        this.userRepository = userRepository;
        this.beanUtils = beanUtils;
    }

    public User createUser(User user) {
        // TODO: 비밀번호 암호화 후 저장 필요
        verifiedExistedEmail(user.getEmail());
        verifiedExistedName(user.getName());
        return userRepository.save(user);
    }

    public void nameDuplicateCheck(String name) {
        verifiedExistedName(name);
    }

    public User findUser(Long userId) {
        return findVerifiedUser(userId);
    }

    public Page<User> findUsers(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("userId").descending());
        return userRepository.findAll(pageRequest);
    }

    public User updateUser(User user) {
        verifiedExistedName(user.getName());
        User findUser = findVerifiedUser(user.getUserId());
        User updateUser = beanUtils.copyNonNullProperties(user, findUser);
        return userRepository.save(updateUser);
    }

    public void removeUser(Long userId) {
        User findUser = findVerifiedUser(userId);
        userRepository.delete(findUser);
    }

    private User findVerifiedUser(Long id) {
        Optional<User> findUser = userRepository.findById(id);
        return findUser.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    public User findVerifiedEmail(String email) {
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
