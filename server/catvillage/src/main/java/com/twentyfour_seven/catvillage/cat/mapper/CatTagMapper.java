package com.twentyfour_seven.catvillage.cat.mapper;

import com.twentyfour_seven.catvillage.cat.dto.CatPostDto;
import com.twentyfour_seven.catvillage.cat.dto.CatTagPostDto;
import com.twentyfour_seven.catvillage.cat.dto.CatTagResponseDto;
import com.twentyfour_seven.catvillage.cat.entity.CatTag;
import com.twentyfour_seven.catvillage.cat.entity.TagToCat;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CatTagMapper {
    default public CatTag tagPostDtoToCatTag(CatTagPostDto catTagPostDto) {
        if (catTagPostDto == null) return null;
        CatTag catTag = new CatTag(catTagPostDto.getTag());
        return catTag;
    }
    public List<CatTag> catTagPostDtosToCatTags(List<CatPostDto> catPostDtos);

    default public CatTagResponseDto catTagToCatTagResponseDto(CatTag catTag) {
        CatTagResponseDto catTagResponseDto = new CatTagResponseDto(catTag.getTagName());
        return catTagResponseDto;
    }

    public List<CatTagResponseDto> catTagsToCatTagResponseDtos(List<CatTag> catTags);

    default public CatTag tagToCatToCatTag(TagToCat tagToCat) {
        return tagToCat.getCatTag();
    }

    public List<CatTag> tagToCatsToCatTags(List<TagToCat> tagToCats);
}
