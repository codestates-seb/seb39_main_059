package com.twentyfour_seven.catvillage.cat.service;

import com.twentyfour_seven.catvillage.cat.entity.Breed;
import com.twentyfour_seven.catvillage.cat.repository.BreedRepository;
import com.twentyfour_seven.catvillage.exception.BusinessLogicException;
import com.twentyfour_seven.catvillage.exception.ExceptionCode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BreedService {
    private final BreedRepository breedRepository;

    public BreedService(BreedRepository breedRepository) {
        this.breedRepository = breedRepository;
    }

    public Breed findByBreedName(String breed) {
        Optional<Breed> optionalBreed = breedRepository.findByKorName(breed);
        Breed findBreed = optionalBreed.orElseThrow(() -> new BusinessLogicException(ExceptionCode.BREED_NOT_FOUND));
        return findBreed;
    }

    public List<Breed> findAll() {
        return breedRepository.findAll();
    }

    public Breed saveBreed(Breed breed) {
        return breedRepository.save(breed);
    }

    public Breed findVerifiedBreed(long breedId) {
        Optional<Breed> optionalBreed = breedRepository.findById(breedId);
        Breed findBreed = optionalBreed.orElseThrow(() -> new BusinessLogicException(ExceptionCode.BREED_NOT_FOUND));
        return findBreed;
    }

    public void removeBreed(long breedId) {
        Breed breed = findVerifiedBreed(breedId);
        breedRepository.delete(breed);
    }
}
