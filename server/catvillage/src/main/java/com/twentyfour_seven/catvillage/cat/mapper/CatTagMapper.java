package com.twentyfour_seven.catvillage.cat.mapper;

import com.twentyfour_seven.catvillage.cat.dto.CatTagPostDto;
import com.twentyfour_seven.catvillage.cat.dto.CatTagResponseDto;
import com.twentyfour_seven.catvillage.cat.entity.CatTag;
import com.twentyfour_seven.catvillage.cat.entity.TagToCat;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CatTagMapper {
    default CatTag tagPostDtoToCatTag(CatTagPostDto catTagPostDto) {
        if (catTagPostDto == null) return null;
        CatTag catTag = new CatTag(catTagPostDto.getTag());
        return catTag;
    }

    List<CatTag> catTagPostDtosToCatTags(List<CatTagPostDto> catTagPostDtos);

    default CatTagResponseDto catTagToCatTagResponseDto(CatTag catTag) {
        CatTagResponseDto catTagResponseDto = new CatTagResponseDto(catTag.getTagName());
        return catTagResponseDto;
    }

    List<CatTagResponseDto> catTagsToCatTagResponseDtos(List<CatTag> catTags);

    default CatTag tagToCatToCatTag(TagToCat tagToCat) {
        return tagToCat.getCatTag();
    }

    List<CatTag> tagToCatsToCatTags(List<TagToCat> tagToCats);

    default CatTagResponseDto tagToCatToCatTagResponseDto(TagToCat tagToCat) {
        if (tagToCat == null) {
            return null;
        }
        return catTagToCatTagResponseDto(tagToCatToCatTag(tagToCat));
    }

    List<CatTagResponseDto> tagToCatsToCatTagResponseDtos(List<TagToCat> tagToCats);
}
