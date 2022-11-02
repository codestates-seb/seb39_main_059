package com.twentyfour_seven.catvillage.catInfo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Validated
public class CatInfoPostDto {
    @Length(max = 20)
    private String korName;

    @Length(max = 20)
    private String engName;

    @Length(max = 1000)
    private String character;

    @Range(max = 3, min = 0)
    private Short hairLength;

    @Range(max = 3, min = 0)
    private Short hairLoss;

    @Length(max = 1000)
    private String features;

    @Valid
    private List<DiseaseDto> disease;
}
