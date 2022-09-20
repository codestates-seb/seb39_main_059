package com.twentyfour_seven.catvillage.cat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CatResponseDto {
    long catId;
    String name;
    int birthYear;
    int birthMonth;
    int birthDay;
    String breed;
    String sex;
    int weight;
    String image;
    String body;
    List<CatTagResponseDto> tags;
}
