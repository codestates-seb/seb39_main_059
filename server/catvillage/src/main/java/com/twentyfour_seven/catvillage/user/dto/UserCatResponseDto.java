package com.twentyfour_seven.catvillage.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCatResponseDto {
    private Long catId;
    private String name;
    private String profileImage;
}
