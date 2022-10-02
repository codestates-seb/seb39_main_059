package com.twentyfour_seven.catvillage.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserMyInfoDto {
    private long userId;
    private String name;
    private String profileImage;
}
