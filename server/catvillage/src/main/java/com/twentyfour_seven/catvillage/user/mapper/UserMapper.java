package com.twentyfour_seven.catvillage.user.mapper;

import com.twentyfour_seven.catvillage.user.dto.*;
import com.twentyfour_seven.catvillage.user.entity.User;
import org.mapstruct.Mapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    default User userPostDtoToUser(UserPostDto requestBody, PasswordEncoder passwordEncoder) {
        if (requestBody == null) {
            return null;
        } else {
            String encodedPassword = passwordEncoder.encode(requestBody.getPassword());
            String profileImage = requestBody.getProfileImage().isEmpty() ?
                    "https://catvillage-image-server.s3.ap-northeast-2.amazonaws.com/catvillage/images/252c5f03-4c0a-4f2a-8874-b928e7dde911-default-user-image.png" :
                    requestBody.getProfileImage();
            return User.builder()
                    .email(requestBody.getEmail())
                    .password(encodedPassword)
                    .name(requestBody.getName())
                    .profileImage(profileImage)
                    .location(requestBody.getLocation())
                    .build();
        }
    }

    default UserPostResponseDto userToUserPostResponseDto(User user) {
        if (user == null) {
            return null;
        } else {
            return UserPostResponseDto.builder()
                    .userId(user.getUserId())
                    .email(user.getEmail())
                    .name(user.getName())
                    .profileImage(user.getProfileImage())
                    .location(user.getLocation())
                    .build();
        }
    }

    default UserGetResponseDto userToUserGetResponseDto(User user) {
        if (user == null) {
            return null;
        } else {
            return UserGetResponseDto.builder()
                    .userId(user.getUserId())
                    .email(user.getEmail())
                    .name(user.getName())
                    .profileImage(user.getProfileImage())
                    .location(user.getLocation())
                    .catCount(user.getCats().size())
                    .contentCount(user.getContentCount()) // TODO : edit after adding feed & board mapping
                    .followerCount(user.getFollowerCount())
                    .followingCount(user.getFollowingCount())
                    .build();
        }
    }

    List<UserGetResponseDto> usersToUserGetResponseDtos(List<User> users);

    default User userPatchDtoToUser(UserPatchDto requestBody) {
        if (requestBody == null) {
            return null;
        } else {
            return User.builder()
                    .password(requestBody.getPassword())
                    .name(requestBody.getName())
                    .location(requestBody.getLocation())
                    .profileImage(requestBody.getProfileImage())
                    .build();
        }
    }

    default UserPatchResponseDto userToUserPatchResponseDto(User user) {
        if (user == null) {
            return null;
        } else {
            return UserPatchResponseDto.builder()
                    .userId(user.getUserId())
                    .name(user.getName())
                    .location(user.getLocation())
                    .profileImage(user.getProfileImage())
                    .build();
        }
    }

    default UserMyInfoDto userToUserMyInfoDto(User user) {
        if (user == null) {
            return null;
        } else {
            return new UserMyInfoDto(user.getUserId(), user.getName(), user.getProfileImage());
        }
    }
}
