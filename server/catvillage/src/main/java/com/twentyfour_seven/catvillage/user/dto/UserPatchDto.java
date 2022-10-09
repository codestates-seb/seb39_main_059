package com.twentyfour_seven.catvillage.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class UserPatchDto {
    @Length(max = 25)
    private String password;
    @Length(max = 16)
    private String name;
    @Length(max = 30)
    private String location;
    @Length(max = 100)
    private String profileImage;

    @Builder
    public UserPatchDto(String password, String name, String location, String profileImage) {
        this.password = password;
        this.name = name;
        this.location = location;
        this.profileImage = profileImage;
    }
}
