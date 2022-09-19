package com.twentyfour_seven.catvillage.cat.service;

import com.twentyfour_seven.catvillage.cat.entity.Breed;
import com.twentyfour_seven.catvillage.cat.entity.Cat;
import com.twentyfour_seven.catvillage.cat.repository.BreedRepository;
import com.twentyfour_seven.catvillage.cat.repository.CatRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CatService {
    private final CatRepository catRepository;
    private final BreedRepository breedRepository;
    private final UserService userService;

    public CatService(CatRepository catRepository, BreedRepository breedRepository, UserService userService) {
        this.catRepository = catRepository;
        this.breedRepository = breedRepository;
        this.userService = userService;
    }

    public Cat saveCat(Cat cat, String breed) {
        User user = userService.findVerifiedUser(userId);
        cat.setUser(user);
        Breed findBreed = findVerifiedBreed(breed);
        cat.setBreed(findBreed);
        Cat createdCat = catRepository.save(cat);
        return createdCat;
    }

    public Breed findVerifiedBreed(String breed) {
        Optional<Breed> optionalBreed = breedRepository.findByKorName(breed);
        Breed findBreed = optionalBreed.orElseThrow(() -> new BusinessLogicException(ExceptionCode.BREED_NOT_FOUND));
        return findBreed;
    }
}
