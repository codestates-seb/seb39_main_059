package com.twentyfour_seven.catvillage.cat.mapper;

import com.twentyfour_seven.catvillage.cat.dto.CatPostDto;
import com.twentyfour_seven.catvillage.cat.entity.Cat;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;
import java.util.Date;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CatMapper {
    default public Cat catPostDtoToCat(CatPostDto catPostDto) {
        if (catPostDto == null) {
            return null;
        }
        LocalDateTime birthDate = LocalDateTime.of(catPostDto.getBirthYear(), catPostDto.getBirthMonth(), catPostDto.getBirthDay(), 0, 0, 0);
        Cat cat = Cat.builder()
                .name(catPostDto.getName())
                .birthDate(birthDate)
                .sex(catPostDto.getSex())
                .weight(catPostDto.getWeight())
                .body(catPostDto.getBody())
                .build();
        return cat;
    }
}
