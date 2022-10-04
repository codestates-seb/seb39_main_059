package com.twentyfour_seven.catvillage.catInfo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CatInfoResponseDto {
    private Long catInfoId;
    private String korName;
    private String engName;
    private String character;
    private Short hairLength;
    private Short hairLoss;
    private String features;
}
