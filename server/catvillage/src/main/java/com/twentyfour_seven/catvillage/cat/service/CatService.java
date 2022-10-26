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

        Cat saveCat = catRepository.save(cat);

        // user에 등록된 대표고양이가 없을 경우 대표 고양이로 등록
        if (findUser.getRepresentCat() == null) {
            userService.updateRepresentCat(findUser, saveCat);
        }

        return saveCat;
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
        // 요청을 보낸 유저가 적절한지 검증
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
        // 요청을 보낸 User 가 등록한 User 와 동일한지 확인
        if (!findCat.getUser().getEmail().equals(email)) {
            throw new BusinessLogicException(ExceptionCode.INVALID_USER);
        }

        Cat updatingCat = beanUtils.copyNonNullProperties(cat, findCat);
        return catRepository.save(updatingCat);
    }

    public List<Cat> findCatsByUser(long userId) {
        // 유저 정보 가져옴
        User findUser = userService.findUser(userId);

        // 유저에 고양이가 없다면 null로 리턴
        if (findUser.getCats() == null || findUser.getCats().isEmpty()) {
            return null;
        }

        List<Cat> resultCats = new ArrayList<>();
        List<Cat> findCats = findUser.getCats();

        // 대표고양이 설정이 안되어 있을 경우 0번 고양이를 대표고양이로 지정
        Cat representCat = findUser.getRepresentCat() == null ? findCats.get(0) : findUser.getRepresentCat();


        // 대표 고양이를 반환할 리스트의 0번 인덱스에 추가
        findCats.remove(representCat);
        resultCats.add(representCat);
        // 대표고양이를 제외한 고양이를 반환할 리스트에 추가
        resultCats.addAll(findCats);

        return resultCats;
    }
}
