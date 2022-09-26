package com.twentyfour_seven.catvillage.user.mapper;

import com.twentyfour_seven.catvillage.user.dto.*;
import com.twentyfour_seven.catvillage.user.entity.User;
import org.mapstruct.Mapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    default User userPostDtoToUser(UserPostDto requestBody, PasswordEncoder passwordEncoder) {
        if(requestBody == null) {
            return null;
        } else {
            String encodedPassword = passwordEncoder.encode(requestBody.getPassword());
            return User.builder()
                    .email(requestBody.getEmail())
                    .password(encodedPassword)
                    .name(requestBody.getName())
                    .profileImage(requestBody.getProfileImage())
                    .location(requestBody.getLocation())
                    .build();
        }
    }

    default UserPostResponseDto userToUserPostResponseDto(User user) {
        if(user == null) {
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
        if(user == null) {
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
        if(requestBody == null) {
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
        if(user == null) {
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
}
