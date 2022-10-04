package com.twentyfour_seven.catvillage.cat.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
public class CatTagPostDto {
    @ApiModelProperty(value = "태그 내용(길이제한 15자)", example = "냥아치")
    @Length(max = 15)
    private String tag;
}
