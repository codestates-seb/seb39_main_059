package com.twentyfour_seven.catvillage.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenRequestDto {
    private String accessToken;
    private String refreshToken;
}
