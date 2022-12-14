package com.twentyfour_seven.catvillage.cat.service;

import com.twentyfour_seven.catvillage.cat.entity.Cat;
import com.twentyfour_seven.catvillage.cat.entity.CatTag;
import com.twentyfour_seven.catvillage.cat.repository.CatRepository;
import com.twentyfour_seven.catvillage.catInfo.entity.CatInfo;
import com.twentyfour_seven.catvillage.catInfo.service.CatInfoService;
import com.twentyfour_seven.catvillage.exception.BusinessLogicException;
import com.twentyfour_seven.catvillage.exception.ExceptionCode;
import com.twentyfour_seven.catvillage.user.entity.User;
import com.twentyfour_seven.catvillage.user.service.UserService;
import com.twentyfour_seven.catvillage.utils.CustomBeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CatService {
    private final CatRepository catRepository;
    private final UserService userService;
    private final CustomBeanUtils<Cat> beanUtils;
    private final CatInfoService catInfoService;

    public CatService(CatRepository catRepository, UserService userService, CustomBeanUtils beanUtils,
                      CatInfoService catInfoService) {
        this.catRepository = catRepository;
        this.userService = userService;
        this.beanUtils = beanUtils;
        this.catInfoService = catInfoService;
    }

    public Cat createCat(Cat cat, String breed, String email) {
        CatInfo findBreed = catInfoService.findVerifiedCatInfo(breed);
        cat.setCatInfo(findBreed);
        User findUser = userService.findVerifiedEmail(email);
        cat.setUser(findUser);

        return catRepository.save(cat);
    }


    public Cat findCat(long catId) {
        return findVerifiedCat(catId);
    }

    public Cat findVerifiedCat(long catId) {
        Optional<Cat> optionalCat = catRepository.findById(catId);
        Cat findCat = optionalCat.orElseThrow(() -> new BusinessLogicException(ExceptionCode.CAT_NOT_FOUND));
        return findCat;
    }

    public void removeCat(long catId, String email) {
        // ????????? ?????? ????????? ???????????? ??????
        userService.findVerifiedEmail(email);
        String userEmail = findVerifiedCat(catId).getUser().getEmail();
        if (email.equals(userEmail)) {
            throw new BusinessLogicException(ExceptionCode.INVALID_USER);
        }

        Cat findCat = findVerifiedCat(catId);
        catRepository.delete(findCat);
    }

    public Cat updateCat(long catId, Cat cat, String breed, String email) {
        Cat findCat = findVerifiedCat(catId);
        CatInfo findBreed = catInfoService.findVerifiedCatInfo(breed);
        cat.setCatInfo(findBreed);
        // ????????? ?????? User ??? ????????? User ??? ???????????? ??????
        if (!findCat.getUser().getEmail().equals(email)) {
            throw new BusinessLogicException(ExceptionCode.INVALID_USER);
        }

        Cat updatingCat = beanUtils.copyNonNullProperties(cat, findCat);
        return catRepository.save(updatingCat);
    }

    public List<Cat> findCatsByUser(long userId) {
        // ?????? ?????? ?????????
        User findUser = userService.findUser(userId);

        // ????????? ???????????? ????????? null??? ??????
        if (findUser.getCats() == null || findUser.getCats().isEmpty()) {
            return null;
        }

        List<Cat> resultCats = new ArrayList<>();
        List<Cat> findCats = findUser.getCats();

        // ??????????????? ????????? ????????? ?????? ?????? 0??? ???????????? ?????????????????? ??????
        Cat representCat = findUser.getRepresentCat() == null ? findCats.get(0) : findUser.getRepresentCat();


        // ?????? ???????????? ????????? ???????????? 0??? ???????????? ??????
        findCats.remove(representCat);
        resultCats.add(representCat);
        // ?????????????????? ????????? ???????????? ????????? ???????????? ??????
        resultCats.addAll(findCats);

        return resultCats;
    }
}
