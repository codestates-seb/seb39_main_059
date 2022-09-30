package com.twentyfour_seven.catvillage.cat.service;

import com.twentyfour_seven.catvillage.cat.entity.Cat;
import com.twentyfour_seven.catvillage.cat.entity.CatTag;
import com.twentyfour_seven.catvillage.cat.repository.CatRepository;
import com.twentyfour_seven.catvillage.exception.BusinessLogicException;
import com.twentyfour_seven.catvillage.exception.ExceptionCode;
import com.twentyfour_seven.catvillage.user.entity.User;
import com.twentyfour_seven.catvillage.user.service.UserService;
import com.twentyfour_seven.catvillage.utils.CustomBeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CatService {
    private final CatRepository catRepository;
    private final UserService userService;
    private final CatTagService catTagService;
    private final CustomBeanUtils<Cat> beanUtils;

    public CatService(CatRepository catRepository, UserService userService, CatTagService catTagService,
                      CustomBeanUtils beanUtils) {
        this.catRepository = catRepository;
        this.userService = userService;
        this.catTagService = catTagService;
        this.beanUtils = beanUtils;
    }

    public Cat createCat(Cat cat, String breed, List<CatTag> catTags, String email) {
        // TODO: breed(CatInfo) 정보 꺼내서 Cat 에 저장 필요
        User findUser = userService.findVerifiedEmail(email);
        cat.setUser(findUser);
        Cat createdCat = catRepository.save(cat);
        catTagService.saveTag(catTags, createdCat);
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
        catRepository.delete(findCat);
    }

    public Cat updateCat(Cat cat, String breed, List<CatTag> catTags, String email) {
        Cat findCat = findVerifiedCat(cat.getCatId());
        // TODO:  CatInfo 구현 후 주석 제거 필요
//        CatInfo findBreed = breedService.findByKorName(breed);
//        cat.setBreed(findBreed);
        // 요청을 보낸 User 가 등록한 User 와 동일한지 확인
        if (findCat.getUser().getEmail() != email) {
            throw new BusinessLogicException(ExceptionCode.INVALID_USER);
        }
        Cat updatingCat = beanUtils.copyNonNullProperties(cat, findCat);
        catTagService.updateTag(catTags, updatingCat);
        return catRepository.save(updatingCat);
    }
}
