package com.twentyfour_seven.catvillage.cat.mapper;

import com.twentyfour_seven.catvillage.cat.dto.CatPostDto;
import com.twentyfour_seven.catvillage.cat.dto.CatTagPostDto;
import com.twentyfour_seven.catvillage.cat.entity.CatTag;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {
    default public CatTag tagPostDtoToCatTag(CatTagPostDto catTagPostDto) {
        if (catTagPostDto == null) return null;
        CatTag catTag = new CatTag(catTagPostDto.getTag());
        return catTag;
    }
    public List<CatTag> catTagPostDtosToCatTags(List<CatPostDto> catPostDtos);
}
