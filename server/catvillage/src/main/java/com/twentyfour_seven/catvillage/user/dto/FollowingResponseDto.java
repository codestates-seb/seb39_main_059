package com.twentyfour_seven.catvillage.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FollowingResponseDto {
    private List<FollowGetResponseDto> following;

    public FollowingResponseDto(List<FollowGetResponseDto> following) {
        this.following = following;
    }
}
