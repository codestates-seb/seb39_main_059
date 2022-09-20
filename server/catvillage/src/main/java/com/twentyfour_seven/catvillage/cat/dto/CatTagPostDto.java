package com.twentyfour_seven.catvillage.cat.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
public class CatTagPostDto {
    @Length(max = 15)
    private String tag;
}
