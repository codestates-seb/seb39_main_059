package com.twentyfour_seven.catvillage.cat.mapper;

import com.twentyfour_seven.catvillage.cat.dto.BreedPostDto;
import com.twentyfour_seven.catvillage.cat.dto.BreedResponseDto;
import com.twentyfour_seven.catvillage.cat.entity.Breed;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BreedMapper {
    default public BreedResponseDto breedToBreedResponseDto(Breed breed) {
        if (breed == null) {
            return null;
        }
        BreedResponseDto breedResponseDto = new BreedResponseDto(breed.getBreedId(),
                breed.getKorName(), breed.getEngName());
        return breedResponseDto;
    }
   public List<BreedResponseDto> breedsToBreedResponseDtos(List<Breed> breeds);

    Breed breedPostDtoToBreed(BreedPostDto breedPostDto);
}
