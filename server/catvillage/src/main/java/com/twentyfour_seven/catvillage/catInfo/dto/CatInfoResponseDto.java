package com.twentyfour_seven.catvillage.catInfo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CatInfoResponseDto {
    @ApiModelProperty(value = "품종 식별자", example = "1")
    private Long catInfoId;

    @ApiModelProperty(value = "한국어 이름", example = "셀커크렉스")
    private String korName;

    @ApiModelProperty(value = "영어 이름", example = "Selkirk Rex")
    private String engName;

    @ApiModelProperty(value = "성격 특징", example = "애교가 많고 외로움을 많이 탑니다.")
    private String character;

    @ApiModelProperty(value = "털길이 0~3(0: 털 없음, 1: 단모, 2: 중모, 3: 장모)", example = "2")
    private Short hairLength;

    @ApiModelProperty(value = "털빠짐 0~3(0: 털 없음, 1: 적음, 2: 중간, 3: 많음)", example = "2")
    private Short hairLoss;

    @ApiModelProperty(value = "그외 특징", example = "코가 낮습니다.")
    private String features;

    @ApiModelProperty(value = "고양이 품종 사진 경로", example = "https://catvillage-image-server.s3.ap-northeast-2.amazonaws.com/catvillage/images/dccf3d4d")
    private String picture;

    @ApiModelProperty(value = "고양이 품종 정보 페이지에 보여주는지 여부", example = "false")
    private Boolean showCatInfo;

    @ApiModelProperty(value = "취약한 질병 리스트")
    private List<DiseaseDto> diseases;
}
