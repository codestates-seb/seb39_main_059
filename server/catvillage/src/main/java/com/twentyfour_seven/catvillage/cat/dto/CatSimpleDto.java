package com.twentyfour_seven.catvillage.cat.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CatSimpleDto {
    private String profileImage;
    private String name;
    @ApiModelProperty(value = "고양이 프로필 링크")
    private String link;
}
