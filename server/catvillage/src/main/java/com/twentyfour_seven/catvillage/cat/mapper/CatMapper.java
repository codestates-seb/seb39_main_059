package com.twentyfour_seven.catvillage.cat.mapper;

import com.twentyfour_seven.catvillage.cat.dto.CatPostDto;
import com.twentyfour_seven.catvillage.cat.dto.CatResponseDto;
import com.twentyfour_seven.catvillage.cat.entity.Cat;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CatMapper {
    default public Cat catPostDtoToCat(CatPostDto catPostDto) {
        if (catPostDto == null) {
            return null;
        }
        String profileImage = (catPostDto.getImage() == null || catPostDto.getImage().isEmpty()) ?
                "https://catvillage-image-server.s3.ap-northeast-2.amazonaws.com/catvillage/images/1ba41d17-5e93-4926-812a-6072e90b7b5b-default-cat-profile.png" :
                catPostDto.getImage();
        LocalDateTime birthDate = LocalDateTime.of(catPostDto.getBirthYear(), catPostDto.getBirthMonth(), catPostDto.getBirthDay(), 0, 0, 0);
        Cat cat = Cat.builder()
                .name(catPostDto.getName())
                .birthDate(birthDate)
                .sex(catPostDto.getSex())
                .weight(catPostDto.getWeight())
                .body(catPostDto.getBody())
                .image(profileImage)
                .build();
        return cat;
    }

    default public CatResponseDto catToCatResponseDto(Cat cat) {
        if (cat == null) {
            return null;
        }
        LocalDateTime birthDate = cat.getBirthDate();
        CatResponseDto catResponseDto = new CatResponseDto(
                cat.getCatId(),
                cat.getName(),
                birthDate.getYear(),
                birthDate.getMonthValue(),
                birthDate.getDayOfMonth(),
                cat.getCatInfo().getKorName(),
                cat.getSex(),
                cat.getWeight(),
                cat.getImage(),
                cat.getBody(),
                new ArrayList<>()
        );
        return catResponseDto;
    }
}
