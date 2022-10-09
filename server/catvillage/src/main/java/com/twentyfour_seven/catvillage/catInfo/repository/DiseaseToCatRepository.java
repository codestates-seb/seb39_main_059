package com.twentyfour_seven.catvillage.catInfo.repository;

import com.twentyfour_seven.catvillage.catInfo.entity.DiseaseToCat;
import com.twentyfour_seven.catvillage.catInfo.entity.DiseaseToCatId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiseaseToCatRepository extends JpaRepository<DiseaseToCat, DiseaseToCatId> {
}
