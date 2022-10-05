package com.twentyfour_seven.catvillage.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FollowerResponseDto {
    private List<FollowGetResponseDto> follower;

    public FollowerResponseDto(List<FollowGetResponseDto> follower) {
        this.follower = follower;
    }
}
