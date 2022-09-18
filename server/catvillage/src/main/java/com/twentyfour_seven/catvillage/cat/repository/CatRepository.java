package com.twentyfour_seven.catvillage.cat.repository;

import com.twentyfour_seven.catvillage.cat.entity.Cat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatRepository extends JpaRepository<Cat, Long> {
}
