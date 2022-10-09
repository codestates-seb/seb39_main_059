package com.twentyfour_seven.catvillage.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FollowGetResponseDto {
    private String name;
    private String link;

    @Builder
    public FollowGetResponseDto(String name, String link) {
        this.name = name;
        this.link = link;
    }
}
