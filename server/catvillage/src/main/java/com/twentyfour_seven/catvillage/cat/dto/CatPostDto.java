package com.twentyfour_seven.catvillage.cat.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Setter
@Validated
public class CatPostDto {
    @Length(max = 16)
    @NotBlank
    private String name;

    private int birthYear;
    private int birthMonth;
    private int birthDay;

    private String breed;

    @Pattern(regexp = "^[mf]$")
    private String sex;

    private int weight;

    @Length(max = 50)
    private String image;

    @Length(max = 1000)
    private String body;

    private List<CatTagPostDto> tags;
}
