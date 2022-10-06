package com.twentyfour_seven.catvillage.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserGetResponseDto {
    @ApiModelProperty(value = "유저 식별자(id)")
    private Long userId;
    @ApiModelProperty(value = "유저 이름")
    private String name;
    @ApiModelProperty(value = "유저 프로필 이미지 경로")
    private String profileImage;
    @ApiModelProperty(value = "고양이 프로필 개수")
    private Integer catCount;
    @ApiModelProperty(value = "작성한 게시글 개수")
    private Long contentCount;
    @ApiModelProperty(value = "유저가 팔로우하고 있는 유저수")
    private Long followingCount;
    @ApiModelProperty(value = "유저를 팔로우하고 있는 유저수")
    private Long followerCount;

    @Builder
    public UserGetResponseDto(Long userId, String name, String profileImage, Integer catCount, Long contentCount, Long followingCount, Long followerCount) {
        this.userId = userId;
        this.name = name;
        this.profileImage = profileImage;
        this.catCount = catCount;
        this.contentCount = contentCount;
        this.followingCount = followingCount;
        this.followerCount = followerCount;
    }
}
