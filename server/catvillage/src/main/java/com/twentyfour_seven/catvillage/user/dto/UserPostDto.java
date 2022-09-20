package com.twentyfour_seven.catvillage.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

@Getter
@Setter
@NoArgsConstructor
public class UserPostDto {
    @Email
    @Length(max = 50)
    private String email;
    @Length(max = 25)
    private String password;
    @Length(max = 16)
    private String name;
    @Length(max = 100)
    private String profileImage;
    @Length(max = 30)
    private String location;

    @Builder
    public UserPostDto(String email, String password, String name, String profileImage, String location) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.profileImage = profileImage;
        this.location = location;
    }
}
