package com.twentyfour_seven.catvillage.catInfo.repository;

import com.twentyfour_seven.catvillage.catInfo.entity.Disease;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiseaseRepository extends JpaRepository<Disease, Long> {
}
