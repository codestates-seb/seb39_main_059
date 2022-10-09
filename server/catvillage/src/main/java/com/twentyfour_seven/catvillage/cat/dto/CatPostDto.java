package com.twentyfour_seven.catvillage.cat.dto;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "고양이 이름(글자제한 16자)", example = "수라", required = true)
    @Length(max = 16)
    @NotBlank
    private String name;

    @ApiModelProperty(value = "고양이 탄생년도", example = "2019")
    private int birthYear;

    @ApiModelProperty(value = "고양이 탄생월", example = "9")
    private int birthMonth;

    @ApiModelProperty(value = "고양이 탄생일", example = "27")
    private int birthDay;

    @ApiModelProperty(value = "고양이 품종명(등록되어 있는 품종만 선택 및 저장가능)", example = "셀커크렉스")
    private String breed;

    @ApiModelProperty(value = "고양이 성별", example = "m")
    @Pattern(regexp = "^[mf]$")
    private String sex;

    @ApiModelProperty(value = "고양이 몸무게(g단위로 전송)", example = "5500")
    private int weight;

    @ApiModelProperty(value = "고양이 프로필 이미지 경로", example = "https://image.catvillage.shop.s3.ap-northeast-2.amazonaws.com/catvillage/images/9f39a517-3555-47b1-a0d4-344e73af1d0b-feed-image-1.jpg")
    @Length(max = 500)
    private String image;

    @ApiModelProperty(value = "고양이 소개글(글자제한 1000자)", example = "안녕하세요! 수라입니다!")
    @Length(max = 1000)
    private String body;

    private List<CatTagPostDto> tags;
}
