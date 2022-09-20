package com.twentyfour_seven.catvillage.cat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BreedResponseDto {
    private long breedId;
    private String korName;
    private String engName;
}
