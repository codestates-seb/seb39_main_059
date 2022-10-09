package com.twentyfour_seven.catvillage.user.mapper;

import com.twentyfour_seven.catvillage.user.dto.FollowGetResponseDto;
import com.twentyfour_seven.catvillage.user.dto.FollowResponseDto;
import com.twentyfour_seven.catvillage.user.entity.Follow;
import com.twentyfour_seven.catvillage.user.entity.User;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface FollowMapper {
    default FollowResponseDto followToFollowResponseDto(Follow follow) {
        if (follow == null) {
            return null;
        } else {
            return FollowResponseDto.builder()
                    .followId(follow.getFollowId())
                    .memberName(follow.getMember().getName())
                    .targetName(follow.getTarget().getName())
                    .build();
        }
    }

    default List<FollowGetResponseDto> followToFollowerGetResponseDto(User user) {
        if (user == null) {
            return null;
        } else {
            return user.getFollower().stream()
                    .map(follower -> FollowGetResponseDto.builder()
                            .name(follower.getMember().getName())
                            .link("https://catvillage.tk/users/" + follower.getMember().getUserId())
                            .build())
                    .collect(Collectors.toList());
        }
    }

    default List<FollowGetResponseDto> followToFollowingGetResponseDto(User user) {
        if (user == null) {
            return null;
        } else {
            return user.getFollowing().stream()
                    .map(follower -> FollowGetResponseDto.builder()
                            .name(follower.getTarget().getName())
                            .link("https://catvillage.tk/users/" + follower.getTarget().getUserId())
                            .build())
                    .collect(Collectors.toList());
        }
    }
}
