package com.twentyfour_seven.catvillage.catInfo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CatInfoSimpleDto {
    private Long catInfoId;
    private String korName;
    private String engName;
}
