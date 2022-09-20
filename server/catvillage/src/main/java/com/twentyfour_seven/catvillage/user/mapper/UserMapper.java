package com.twentyfour_seven.catvillage.user.mapper;

import com.twentyfour_seven.catvillage.user.dto.UserPostDto;
import com.twentyfour_seven.catvillage.user.dto.UserPostResponseDto;
import com.twentyfour_seven.catvillage.user.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    default User userPostDtoToUser(UserPostDto requestBody) {
        if(requestBody == null) {
            return null;
        } else {
            return User.builder()
                    .email(requestBody.getEmail())
                    .password(requestBody.getPassword())
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
}
