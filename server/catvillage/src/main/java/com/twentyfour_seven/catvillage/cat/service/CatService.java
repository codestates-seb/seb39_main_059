package com.twentyfour_seven.catvillage.cat.service;

import com.twentyfour_seven.catvillage.cat.entity.Breed;
import com.twentyfour_seven.catvillage.cat.entity.Cat;
import com.twentyfour_seven.catvillage.cat.entity.CatTag;
import com.twentyfour_seven.catvillage.cat.repository.CatRepository;
import com.twentyfour_seven.catvillage.exception.BusinessLogicException;
import com.twentyfour_seven.catvillage.exception.ExceptionCode;
import com.twentyfour_seven.catvillage.user.entity.User;
import com.twentyfour_seven.catvillage.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CatService {
    private final CatRepository catRepository;

    private final BreedService breedService;
    private final UserService userService;
    private final CatTagService catTagService;

    public CatService(CatRepository catRepository, BreedService breedService,
                      UserService userService, CatTagService catTagService) {
        this.catRepository = catRepository;
        this.breedService = breedService;
        this.userService = userService;
        this.catTagService = catTagService;
    }

    public Cat saveCat(Cat cat, String breed, List<CatTag> catTags) {
        User user = userService.findVerifiedUser(userId);
        cat.setUser(user);
        Breed findBreed = breedService.findVerifiedBreed(breed);
        cat.setBreed(findBreed);
        catTagService.saveTag(catTags, cat);
        Cat createdCat = catRepository.save(cat);
        return createdCat;
    }


    public Cat findCat(long catId) {
        return findVerifiedCat(catId);
    }

    public Cat findVerifiedCat(long catId) {
        Optional<Cat> optionalCat = catRepository.findById(catId);
        Cat findCat = optionalCat.orElseThrow(() -> new BusinessLogicException(ExceptionCode.CAT_NOT_FOUND));
        return findCat;
    }

    public void removeCat(long catId) {
        Cat findCat = findVerifiedCat(catId);
        catTagService.removeTagToCats(findCat.getTagToCats());
        catRepository.delete(findCat);
    }
}
