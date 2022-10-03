package com.twentyfour_seven.catvillage.catInfo.repository;

import com.twentyfour_seven.catvillage.catInfo.entity.CatInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CatInfoRepository extends JpaRepository<CatInfo, Long> {
    Optional<CatInfo> findByKorName(String korName);
}
