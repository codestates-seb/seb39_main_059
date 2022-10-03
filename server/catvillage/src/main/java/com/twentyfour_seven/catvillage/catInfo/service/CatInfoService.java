package com.twentyfour_seven.catvillage.catInfo.service;

import com.twentyfour_seven.catvillage.catInfo.entity.CatInfo;
import com.twentyfour_seven.catvillage.catInfo.repository.CatInfoRepository;
import com.twentyfour_seven.catvillage.exception.BusinessLogicException;
import com.twentyfour_seven.catvillage.exception.ExceptionCode;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CatInfoService {

    private final CatInfoRepository catInfoRepository;

    public CatInfoService(CatInfoRepository catInfoRepository) {
        this.catInfoRepository = catInfoRepository;
    }

    public CatInfo createCatInfo(CatInfo catInfo) {
        verifiedExistKorName(catInfo.getKorName());
        return catInfoRepository.save(catInfo);
    }

    private void verifiedExistKorName(String korName) {
        catInfoRepository.findByKorName(korName)
                .ifPresent(a -> {
                    throw new BusinessLogicException(ExceptionCode.BREED_EXISTS);
                });
    }
}
