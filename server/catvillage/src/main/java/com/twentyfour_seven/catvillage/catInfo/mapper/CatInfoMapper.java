package com.twentyfour_seven.catvillage.catInfo.mapper;

import com.twentyfour_seven.catvillage.catInfo.dto.CatInfoPostDto;
import com.twentyfour_seven.catvillage.catInfo.dto.CatInfoResponseDto;
import com.twentyfour_seven.catvillage.catInfo.entity.CatInfo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CatInfoMapper {

    CatInfo catInfoPostDtoToCatInfo(CatInfoPostDto catInfoPostDto);

    CatInfoResponseDto catInfoToCatInfoResponseDto(CatInfo catInfo);
}
