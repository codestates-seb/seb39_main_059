package com.twentyfour_seven.catvillage.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserPatchDto {
    private String password;
    private String name;
    private String location;
    private String profileImage;

    @Builder
    public UserPatchDto(String password, String name, String location, String profileImage) {
        this.password = password;
        this.name = name;
        this.location = location;
        this.profileImage = profileImage;
    }
}
