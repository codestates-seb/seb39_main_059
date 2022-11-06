package com.twentyfour_seven.catvillage.catInfo.dto;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "한국어 이름(길이 20자 제한)", example = "셀커크 렉스")
    @Length(max = 20)
    private String korName;

    @ApiModelProperty(value = "영어 이름(길이 20자 제한", example = "Selkirk Rex")
    @Length(max = 20)
    private String engName;

    @ApiModelProperty(value = "성격 특징(길이 1000자 제한", example = "애교가 많고 외로움을 많이 탑니다.")
    @Length(max = 1000)
    private String character;

    @ApiModelProperty(value = "털길이 0~3(0: 털 없음, 1: 단모, 2: 중모, 3: 장모)", example = "2")
    @Range(max = 3, min = 0)
    private Short hairLength;

    @ApiModelProperty(value = "털길이 0~3(0: 털 없음, 1: 단모, 2: 중모, 3: 장모)", example = "2")
    @Range(max = 3, min = 0)
    private Short hairLoss;

    @ApiModelProperty(value = "그외 특징", example = "코가 낮습니다.")
    @Length(max = 1000)
    private String features;

    @ApiModelProperty(value = "고양이 품종 정보 페이지에 보여주는지 여부", example = "false")
    private boolean showCatInfo;

    @ApiModelProperty(value = "고양이 품종 사진 경로", example = "https://catvillage-image-server.s3.ap-northeast-2.amazonaws.com/catvillage/images/dccf3d4d")
    private String picture;

    @ApiModelProperty(value = "취약한 질병 리스트")
    @Valid
    private List<DiseaseDto> diseases;
}
