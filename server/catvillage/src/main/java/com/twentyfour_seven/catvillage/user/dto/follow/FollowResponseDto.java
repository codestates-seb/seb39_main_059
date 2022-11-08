package com.twentyfour_seven.catvillage.user.dto.follow;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FollowResponseDto {
    private Long followId;
    private String memberName;
    private String targetName;

    @Builder
    public FollowResponseDto(Long followId, String memberName, String targetName) {
        this.followId = followId;
        this.memberName = memberName;
        this.targetName = targetName;
    }
}
