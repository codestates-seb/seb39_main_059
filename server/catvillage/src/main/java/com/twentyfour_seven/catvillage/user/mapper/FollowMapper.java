package com.twentyfour_seven.catvillage.user.mapper;

import com.twentyfour_seven.catvillage.user.dto.FollowResponseDto;
import com.twentyfour_seven.catvillage.user.entity.Follow;
import org.mapstruct.Mapper;

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
}
