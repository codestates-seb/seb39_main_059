package com.twentyfour_seven.catvillage.catInfo.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class DiseaseDto {
    @Length(max = 30)
    private String name;
}
