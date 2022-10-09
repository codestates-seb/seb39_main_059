package com.twentyfour_seven.catvillage.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class UserPatchResponseDto {
    private Long userId;
    private String name;
    private String location;
    private String profileImage;

    @Builder
    public UserPatchResponseDto(Long userId, String name, String location, String profileImage) {
        this.userId = userId;
        this.name = name;
        this.location = location;
        this.profileImage = profileImage;
    }
}
