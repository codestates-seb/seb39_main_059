package com.twentyfour_seven.catvillage.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserGetResponseDto {
    private Long userId;
    private String email;
    private String name;
    private String profileImage;
    private String location;
    private Integer catCount;
    private Long contentCount;
    private Long followingCount;
    private Long followerCount;

    @Builder
    public UserGetResponseDto(Long userId, String email, String name, String profileImage, String location, Integer catCount, Long contentCount, Long followingCount, Long followerCount) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.profileImage = profileImage;
        this.location = location;
        this.catCount = catCount;
        this.contentCount = contentCount;
        this.followingCount = followingCount;
        this.followerCount = followerCount;
    }
}
