package com.twentyfour_seven.catvillage.catInfo.repository;

import com.twentyfour_seven.catvillage.catInfo.entity.CatInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatInfoRepository extends JpaRepository<CatInfo, Long> {
}
