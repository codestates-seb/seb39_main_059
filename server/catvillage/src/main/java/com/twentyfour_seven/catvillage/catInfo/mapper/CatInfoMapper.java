package com.twentyfour_seven.catvillage.catInfo.mapper;

import com.twentyfour_seven.catvillage.catInfo.dto.CatInfoPostDto;
import com.twentyfour_seven.catvillage.catInfo.dto.CatInfoResponseDto;
import com.twentyfour_seven.catvillage.catInfo.dto.CatInfoSimpleDto;
import com.twentyfour_seven.catvillage.catInfo.dto.DiseaseDto;
import com.twentyfour_seven.catvillage.catInfo.entity.CatInfo;
import com.twentyfour_seven.catvillage.catInfo.entity.Disease;
import com.twentyfour_seven.catvillage.catInfo.entity.DiseaseToCat;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CatInfoMapper {

    CatInfo catInfoPostDtoToCatInfo(CatInfoPostDto catInfoPostDto);

    default CatInfoResponseDto catInfoToCatInfoResponseDto(CatInfo catInfo) {
        if (catInfo == null)    return null;
        List<DiseaseDto> diseaseDtos = diseasesToDiseaseDtos(diseaseToCatsToDiseases(catInfo.getDiseaseToCats()));
        return CatInfoResponseDto.builder()
                .catInfoId(catInfo.getCatInfoId())
                .korName(catInfo.getKorName())
                .engName(catInfo.getEngName())
                .character(catInfo.getCharacter())
                .hairLength(catInfo.getHairLength())
                .hairLoss(catInfo.getHairLoss())
                .features(catInfo.getFeatures())
                .diseases(diseaseDtos)
                .picture(catInfo.getPicture())
                .showCatInfo(catInfo.isShowCatInfo())
                .build();
    }

    List<CatInfoResponseDto> catInfosToCatInfoResponseDtos(List<CatInfo> catInfos);
    default DiseaseDto diseaseToDiseaseDto(Disease disease) {
        if (disease == null)    return null;
        return new DiseaseDto(disease.getName());
    }
    List<DiseaseDto> diseasesToDiseaseDtos(List<Disease> disease);

    default Disease diseaseToCatToDisease(DiseaseToCat diseaseToCat) {
        if (diseaseToCat == null) return null;
        return diseaseToCat.getDisease();
    }
    List<Disease> diseaseToCatsToDiseases(List<DiseaseToCat> diseaseToCats);

    default CatInfoSimpleDto catInfoToCatInfoSimpleDto(CatInfo catInfo) {
        if (catInfo == null) return null;
        return new CatInfoSimpleDto(catInfo.getCatInfoId(), catInfo.getKorName(), catInfo.getEngName());
    }

    List<CatInfoSimpleDto> catInfosToCatInfoSimpleDtos(List<CatInfo> catInfos);
}