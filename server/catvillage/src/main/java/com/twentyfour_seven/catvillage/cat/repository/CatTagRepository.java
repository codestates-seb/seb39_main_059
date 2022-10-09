package com.twentyfour_seven.catvillage.cat.repository;

import com.twentyfour_seven.catvillage.cat.entity.CatTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CatTagRepository extends JpaRepository<CatTag, Long> {
    Optional<CatTag> findByTagName(String tagName);
}
