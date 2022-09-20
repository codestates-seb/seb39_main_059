package com.twentyfour_seven.catvillage.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserPostResponseDto {
    private Long userId;
    private String email;
    private String name;
    private String profileImage;
    private String location;

    @Builder
    public UserPostResponseDto(Long userId, String email, String name, String profileImage, String location) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.profileImage = profileImage;
        this.location = location;
    }
}
