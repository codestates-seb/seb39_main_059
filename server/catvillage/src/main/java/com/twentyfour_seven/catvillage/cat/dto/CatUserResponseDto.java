package com.twentyfour_seven.catvillage.cat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CatUserResponseDto {
    private CatResponseDto representCat;
    private List<CatSimpleDto> cats;
}
